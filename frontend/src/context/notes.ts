import { createContext } from "react";
import { NoteType } from "../types";

type NotesContextType = {
  notes: NoteType[];
  selectedNote: NoteType | null;
  useSocket: boolean;
  setUseSocket: (value: boolean) => void;
  setSelectedNote: (note: NoteType | null) => void;
  setNotes: (value: NoteType[]) => void;
  updateNote: (note: NoteType) => void;
  deleteNote: (noteId: string) => void;
};

const NotesContext = createContext<NotesContextType>({
  notes: [],
  selectedNote: null,
  setSelectedNote: () => {},
  useSocket: false,
  setUseSocket: () => {},
  setNotes: () => {},
  updateNote: () => {},
  deleteNote: () => {},
});

export default NotesContext;
