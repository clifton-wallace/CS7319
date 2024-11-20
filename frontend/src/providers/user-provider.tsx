import { useEffect, useState } from "react";
import UserContext from "../context/user";
import { UserType } from "../types";
import { getUserById, loginUser, registerUser } from "../helpers/users";
import { useLocation, useNavigate } from "react-router-dom";

type Props = {
  children: React.ReactNode;
};

export default function UserProvider({ children }: Props) {
  const [user, setUser] = useState<UserType | null>(null);
  const navigate = useNavigate();
  const location = useLocation();

  const login = async (email: string, password: string) => {
    const { data, success } = await loginUser({
      emailAddress: email,
      password,
    });

    if (success) {
      setUser(data);
      localStorage.setItem("ln-user", data.id.toString());
      navigate("/");
      return true;
    }

    return false;
  };

  const logout = () => {
    setUser(null);
    localStorage.removeItem("ln-user");
    navigate("/login");
  };

  const register = async (email: string, password: string) => {
    const { data: userId, success } = await registerUser({
      email,
      password,
      role: "USER",
    });

    const { data: user, success: userSuccess } = await getUserById(userId);

    if (success && userSuccess) {
      setUser(user);
      localStorage.setItem("ln-user", user.id.toString());
      navigate("/");
      return true;
    }

    return false;
  };

  useEffect(() => {
    const localUserId = localStorage.getItem("ln-user");

    if (localUserId) {
      getUserById(Number.parseInt(localUserId)).then(({ data, success }) => {
        if (success) {
          setUser(data);
          localStorage.setItem("ln-user", data.id.toString());
          navigate("/");
        }
      });
    } else if (
      location.pathname !== "/login" &&
      location.pathname !== "/register"
    ) {
      navigate("/login");
    }
  }, [location.pathname, navigate]);

  return (
    <UserContext.Provider value={{ user, login, logout, register }}>
      {children}
    </UserContext.Provider>
  );
}
