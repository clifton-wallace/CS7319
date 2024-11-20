import "../styles/login.css";
import { useState } from "react";
import useUser from "../hooks/use-user";
import { Link } from "react-router-dom";

export default function Login() {
  const [loading, setLoading] = useState(false);
  const { login } = useUser();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    setLoading(true);

    const form = new FormData(event.currentTarget);
    const email = form.get("email") as string;
    const password = form.get("password") as string;

    const success = await login(email, password);

    if (!success) {
      alert("Invalid email or password");
      setLoading(false);
    }
  };

  return (
    <main className="login">
      <h1>Login to your account</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="email">Email</label>
          <input className="textentry" type="email" id="email" name="email" />
        </div>
        <div className="form-group">
          <label htmlFor="password">Password</label>
          <input
            className="textentry"
            type="password"
            id="password"
            name="password"
          />
        </div>
        <button className="btn" type="submit">
          {loading ? "Loading..." : "Login"}
        </button>
        <p>
          Don't have an account? <Link to="/register">Register</Link>
        </p>
      </form>
    </main>
  );
}
