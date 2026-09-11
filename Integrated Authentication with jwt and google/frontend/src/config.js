// Backend base URL. The JWT lives in an httpOnly "token" cookie set by the
// backend, so every request below is made with credentials: 'include'.
export const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080";

// Full-page navigation target for the Google login button.
export const GOOGLE_LOGIN_URL = `${API_BASE_URL}/oauth2/authorization/google`;
