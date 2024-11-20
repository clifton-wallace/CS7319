import { createContext } from "react";
import { UserType } from "../types";

type UserContextType = {
  user: UserType | null;
  login: (email: string, password: string) => Promise<boolean>;
  logout: () => void;
  register: (email: string, password: string) => Promise<boolean>;
};

const UserContext = createContext<UserContextType>({
  user: null,
  login: async () => false,
  logout: () => {},
  register: async () => false,
});

export default UserContext;
