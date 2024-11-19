import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./index.css";
import App from "./routes/App.tsx";
import Login from "./routes/Login.tsx";
import Register from "./routes/Register.tsx";
import NotesProvider from "./providers/notes-provider.tsx";

const router = createBrowserRouter([
  {
    path: "/",
    element: <App />,
  },
  {
    path: "/login",
    element: <Login />,
  },
  {
    path: "/register",
    element: <Register />,
  },
]);

createRoot(document.getElementById("root")!).render(
  <NotesProvider>
    <RouterProvider router={router} />
  </NotesProvider>
);
