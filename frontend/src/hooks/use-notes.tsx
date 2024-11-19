import { useContext } from "react";
import NotesContext from "../context/notes";

export default function useNotes() {
  return useContext(NotesContext);
}
