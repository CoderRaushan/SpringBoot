import { useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";

export default function Home() {
  const { user, logout } = useAuth();
  const navigate = useNavigate();

  async function handleLogout() {
    await logout();
    navigate("/login");
  }

  return (
    <div className="page-center">
      <div className="auth-card">
        <h1>Welcome{user?.name ? `, ${user.name}` : ""}!</h1>
        <p className="info-text">You're logged in.</p>

        <div className="profile-box">
          <div>
            <span className="label">Name</span>
            <span>{user?.name || "—"}</span>
          </div>
          <div>
            <span className="label">Email</span>
            <span>{user?.email}</span>
          </div>
          <div>
            <span className="label">Signed in with</span>
            <span>{user?.provider}</span>
          </div>
        </div>

        <button onClick={handleLogout} className="btn btn-primary">
          Logout
        </button>
      </div>
    </div>
  );
}
