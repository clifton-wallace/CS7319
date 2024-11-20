import { useState } from "react";
import "../styles/login.css";
import useUser from "../hooks/use-user";

export default function Register() {
  const [loading, setLoading] = useState(false);
  const { register } = useUser();

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    setLoading(true);

    const form = new FormData(event.currentTarget);
    const email = form.get("emailAddress") as string;
    const password = form.get("password") as string;
    const confirmPassword = form.get("confirm-password") as string;

    if (password !== confirmPassword) {
      alert("Passwords do not match");
      setLoading(false);
      return;
    }

    const success = await register(email, password);

    if (!success) {
      alert("Failed to register");
      setLoading(false);
    }
  };

  return (
    <main className="login">
      <h1>Register account</h1>
      <form className="login-form" onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="emailAddress">Email</label>
          <input
            className="textentry"
            type="email"
            id="emailAddress"
            name="emailAddress"
          />
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
        <div className="form-group">
          <label htmlFor="confirm-password">Confirm Password</label>
          <input
            className="textentry"
            type="password"
            id="confirm-password"
            name="confirm-password"
          />
        </div>
        <button className="btn" type="submit">
          {loading ? "Loading..." : "Register"}
        </button>
      </form>
    </main>
  );
}
