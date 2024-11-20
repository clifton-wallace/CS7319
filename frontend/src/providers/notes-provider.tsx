import { useEffect, useState } from "react";
import NotesContext from "../context/notes";
import { NotesBroadcast, NoteType } from "../types";
import useUser from "../hooks/use-user";

type Props = {
  children: React.ReactNode;
};

export default function NotesProvider({ children }: Props) {
  const [notes, setNotes] = useState<NoteType[]>([]);
  const [selectedNote, setSelectedNote] = useState<NoteType | null>(null);
  const [useSocket, setUseSocket] = useState(false);
  const [socket, setSocket] = useState<WebSocket | null>(null);
  const { user } = useUser();

  const updateNote = (note: NoteType) => {
    const newNotes = notes.map((n) => (n.id === note.id ? note : n));
    setNotes(newNotes);

    if (selectedNote?.id === note.id) {
      setSelectedNote(note);
    }
  };

  const deleteNote = (noteId: string) => {
    const newNotes = notes.filter((note) => note.id !== noteId);
    setNotes(newNotes);

    if (selectedNote?.id === noteId) {
      setSelectedNote(null);
    }
  };

  useEffect(() => {
    if (useSocket) {
      // Connect to socket server
      const socket = new WebSocket(
        import.meta.env.VITE_NOTES_WS_ENDPOINT as string
      );

      socket.addEventListener("message", (event) => {
        if (!user) return;
        const { note, action } = JSON.parse(event.data) as NotesBroadcast;

        if (action === "UPDATE") {
          if (note.lastUpdatedByUserId != user.id) {
            updateNote(note);
          }
        } else if (action === "DELETE") {
          deleteNote(note.id);
        } else if (action === "CREATE") {
          setNotes([...notes, note]);

          if (!selectedNote) {
            setSelectedNote(note);
          }
        }

        console.log("Message from server ", event.data);
      });

      socket.addEventListener("open", () => {
        console.log("Connected to socket server");
      });

      setSocket(socket);
      console.log("Socket connected");

      return () => {
        socket.close();
      };
    } else {
      // Disconnect from socket server
      if (socket) {
        socket.close();
        setSocket(null);

        console.log("Socket disconnected");
      }
    }
  }, [useSocket]);

  // Sort notes by lastUpdated date
  const sortedNotes = notes.sort((a, b) => {
    return (
      new Date(b.lastUpdated).getTime() - new Date(a.lastUpdated).getTime()
    );
  });

  return (
    <NotesContext.Provider
      value={{
        notes: sortedNotes,
        setNotes,
        selectedNote,
        setSelectedNote,
        useSocket,
        setUseSocket,
        updateNote,
        deleteNote,
      }}
    >
      {children}
    </NotesContext.Provider>
  );
}
