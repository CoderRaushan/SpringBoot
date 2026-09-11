import { API_BASE_URL } from "./config";

async function parseJsonSafe(response) {
  const text = await response.text();
  if (!text) return null;
  try {
    return JSON.parse(text);
  } catch {
    return null;
  }
}

async function request(path, options = {}) {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    credentials: "include",
    headers: { "Content-Type": "application/json" },
    ...options,
  });

  const data = await parseJsonSafe(response);

  if (!response.ok) {
    const message = data?.message || "Something went wrong";
    throw new Error(message);
  }

  return data;
}

export function registerUser({ name, email, password }) {
  return request("/api/auth/register", {
    method: "POST",
    body: JSON.stringify({ name, email, password }),
  });
}

export function loginUser({ email, password }) {
  return request("/api/auth/login", {
    method: "POST",
    body: JSON.stringify({ email, password }),
  });
}

export function logoutUser() {
  return request("/api/auth/logout", { method: "POST" });
}

export function fetchCurrentUser() {
  return request("/api/auth/me", { method: "GET" });
}
