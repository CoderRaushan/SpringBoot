# LoginIntegration — JWT (email/password) + Google Login merged

This project merges your two Spring Boot apps into one backend, plus a new
React (Vite) frontend with a single login page.

```
LoginIntegration/
├── backend/     Spring Boot 4.1.0 (Java 21) — Maven project
└── frontend/    React + Vite
```

## How login works

- **Email/password**: `POST /api/auth/login` authenticates against the DB
  (BCrypt), issues a JWT, and sets it in an **httpOnly cookie named `token`**.
- **Google**: clicking "Login with Google" sends the browser to
  `GET /oauth2/authorization/google`. After Google redirects back, the
  backend creates/updates the user, issues the **same kind of JWT**, sets the
  **same `token` cookie**, then redirects the browser to the React app
  (`http://localhost:5173/home`).
- Every following request from the frontend (`GET /api/auth/me`,
  `POST /api/auth/logout`, etc.) is sent with `credentials: 'include'`, so
  the browser attaches the `token` cookie automatically — the frontend never
  touches the raw JWT.

## 1. Backend setup

1. Create the database (matches your original projects):
   ```sql
   CREATE DATABASE Login_Integration_DB;
   ```
2. Check `backend/src/main/resources/application.properties`:
   - `spring.datasource.username` / `password` — your MySQL creds.
   - `spring.security.oauth2.client.registration.google.client-id` /
     `client-secret` — carried over from your `LoginWithGoogle` project.
     ⚠️ **This secret was inside the zip you uploaded, so treat it as already
     exposed — rotate it in Google Cloud Console and set it via the
     `GOOGLE_CLIENT_ID` / `GOOGLE_CLIENT_SECRET` environment variables
     instead of leaving it in the file, especially before sharing this
     project or pushing it anywhere public.**
   - In Google Cloud Console, make sure the authorized redirect URI is:
     `http://localhost:8080/login/oauth2/code/google`
3. Run it:
   ```bash
   cd backend
   ./mvnw spring-boot:run
   ```
   Backend runs on `http://localhost:8080`. `ROLE_USER` is auto-seeded on
   startup, so registration works immediately — no manual DB insert needed.

## 2. Frontend setup

```bash
cd frontend
npm install
npm run dev
```
Runs on `http://localhost:5173` (already the default `app.frontend-url` /
`app.frontend-oauth-redirect` in the backend config — no changes needed for
local dev).

## 3. Try it

1. Open `http://localhost:5173/login`.
2. Register with name/email/password, then log in — or click **Login with
   Google**.
3. Either path lands you on `/home`, which calls `GET /api/auth/me` to show
   your name, email, and which provider you signed in with.
4. **Logout** clears the `token` cookie and sends you back to `/login`.

## Notes / things worth knowing

- One `users` table serves both login types. A Google sign-in with an email
  that already has a local password just links the Google account to the
  same row (`providerSubject` gets filled in) instead of creating a
  duplicate user.
- CSRF protection is disabled — the `token` cookie is `SameSite=Lax`, which
  blocks cross-site state-changing requests, so this is fine for local dev.
  For production, also flip `app.cookie-secure=true` once you're on HTTPS.
- If you deploy the frontend/backend on different origins in production,
  update `app.frontend-url`, `app.frontend-oauth-redirect`,
  `app.frontend-login-url` in `application.properties`, and
  `VITE_API_BASE_URL` in `frontend/.env`.
