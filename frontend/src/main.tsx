import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import App from "./routes/App.tsx";
import Login from "./routes/Login.tsx";
import Register from "./routes/Register.tsx";
import NotesProvider from "./providers/notes-provider.tsx";
import UserProvider from "./providers/user-provider.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <UserProvider>
        <NotesProvider>
          <App />
        </NotesProvider>
      </UserProvider>
    ),
  },
  {
    path: "/login",
    element: (
      <UserProvider>
        <Login />
      </UserProvider>
    ),
  },
  {
    path: "/register",
    element: (
      <UserProvider>
        <Register />
      </UserProvider>
    ),
  },
]);

createRoot(document.getElementById("root")!).render(
  <RouterProvider router={router} />
);
