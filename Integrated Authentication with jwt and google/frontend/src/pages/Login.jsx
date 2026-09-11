import { useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { loginUser, registerUser } from "../api";
import { GOOGLE_LOGIN_URL } from "../config";
import { useAuth } from "../context/AuthContext";

export default function Login() {
  const navigate = useNavigate();
  const { refreshUser } = useAuth();
  const [searchParams] = useSearchParams();
  const oauthError = searchParams.get("error");

  const [mode, setMode] = useState("login"); // "login" | "register"
  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [info, setInfo] = useState("");
  const [submitting, setSubmitting] = useState(false);

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");
    setInfo("");
    setSubmitting(true);

    try {
      if (mode === "register") {
        await registerUser({ name, email, password });
        setInfo("Account created! You can now log in.");
        setMode("login");
        setPassword("");
      } else {
        await loginUser({ email, password });
        await refreshUser();
        navigate("/home");
      }
    } catch (err) {
      setError(err.message);
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="page-center">
      <div className="auth-card">
        <h1>{mode === "login" ? "Log in" : "Create account"}</h1>

        {oauthError && <p className="error-text">{oauthError}</p>}
        {error && <p className="error-text">{error}</p>}
        {info && <p className="info-text">{info}</p>}

        <form onSubmit={handleSubmit} className="auth-form">
          {mode === "register" && (
            <label>
              Name
              <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                required
              />
            </label>
          )}

          <label>
            Email
            <input
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              required
            />
          </label>

          <label>
            Password
            <input
              type="password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
              minLength={6}
            />
          </label>

          <button type="submit" disabled={submitting} className="btn btn-primary">
            {submitting ? "Please wait..." : mode === "login" ? "Log in" : "Register"}
          </button>
        </form>

        <div className="divider">
          <span>OR</span>
        </div>

        <a href={GOOGLE_LOGIN_URL} className="btn btn-google">
          <GoogleIcon />
          Login with Google
        </a>

        <p className="switch-mode">
          {mode === "login" ? (
            <>
              Don't have an account?{" "}
              <button type="button" className="link-btn" onClick={() => { setMode("register"); setError(""); setInfo(""); }}>
                Register
              </button>
            </>
          ) : (
            <>
              Already have an account?{" "}
              <button type="button" className="link-btn" onClick={() => { setMode("login"); setError(""); setInfo(""); }}>
                Log in
              </button>
            </>
          )}
        </p>
      </div>
    </div>
  );
}

function GoogleIcon() {
  return (
    <svg width="18" height="18" viewBox="0 0 18 18" aria-hidden="true">
      <path fill="#4285F4" d="M17.64 9.2c0-.64-.06-1.25-.16-1.84H9v3.48h4.84c-.21 1.13-.84 2.09-1.8 2.73v2.27h2.92c1.7-1.57 2.68-3.88 2.68-6.64z" />
      <path fill="#34A853" d="M9 18c2.43 0 4.47-.8 5.96-2.18l-2.92-2.27c-.81.54-1.84.86-3.04.86-2.34 0-4.32-1.58-5.03-3.71H.96v2.34C2.44 15.98 5.48 18 9 18z" />
      <path fill="#FBBC05" d="M3.97 10.7c-.18-.54-.28-1.11-.28-1.7s.1-1.16.28-1.7V4.96H.96A8.996 8.996 0 000 9c0 1.45.35 2.83.96 4.04l3.01-2.34z" />
      <path fill="#EA4335" d="M9 3.58c1.32 0 2.51.45 3.44 1.35l2.58-2.58C13.46.89 11.43 0 9 0 5.48 0 2.44 2.02.96 4.96l3.01 2.34C4.68 5.16 6.66 3.58 9 3.58z" />
    </svg>
  );
}
