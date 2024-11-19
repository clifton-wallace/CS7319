import { useState } from "react";
import { loginUser } from "../helpers/users";
import "../styles/login.css";

export default function Login() {
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
    event.preventDefault();

    setLoading(true);

    const form = new FormData(event.currentTarget);
    const email = form.get("email") as string;
    const password = form.get("password") as string;

    const { data } = await loginUser({ emailAddress: email, password });
    console.log(data);

    setLoading(false);
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
      </form>
    </main>
  );
}
