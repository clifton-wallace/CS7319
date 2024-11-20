import "../styles/app.css";
import { useEffect } from "react";
import { NoteType } from "../types";
import { createNote, deleteNote, getNotes } from "../helpers/notes";
import useNotes from "../hooks/use-notes";
import NoteEditor from "../components/NoteEditor";
import moment from "moment-timezone";
import useUser from "../hooks/use-user";

export default function App() {
  const {
    notes,
    setNotes,
    deleteNote: deleteOurNote,
    selectedNote,
    setSelectedNote,
    useSocket,
    setUseSocket,
  } = useNotes();
  const { user, logout } = useUser();

  useEffect(() => {
    const fetchNotes = async () => {
      const { data } = await getNotes();
      setNotes(data);
    };

    fetchNotes();
  }, []);

  const handleNoteSelect = (note: NoteType) => {
    setSelectedNote(note);
  };

  const handleCreateNote = async () => {
    if (!user) return;

    const { data } = await createNote({
      title: "New Note",
      content: "",
      userId: user.id,
    });

    setNotes([...notes, data]);
  };

  const handleDeleteNote = async () => {
    if (!selectedNote) return;

    const { success } = await deleteNote(selectedNote.id);
    if (!success) {
      return alert("Failed to delete note");
    }

    deleteOurNote(selectedNote.id);
    alert("Note deleted successfully");
  };

  const toggleSocket = () => {
    setUseSocket(!useSocket);
  };

  const lastUpdatedFormatted = selectedNote
    ? moment(selectedNote.lastUpdated).subtract(6, "hours").fromNow()
    : null;

  return (
    <div className="layout">
      <div className="sidebar">
        <h1>
          <a href="/">Live Notes</a>
        </h1>
        <nav>
          <ul>
            <li>
              <button onClick={handleCreateNote}>+ New Note</button>
            </li>
            {notes.length > 0 && <div className="divider"></div>}
            {notes.map((note) => (
              <li key={note.id}>
                <button onClick={() => handleNoteSelect(note)}>
                  {note.title}
                </button>
              </li>
            ))}
          </ul>
        </nav>
        <div className="actions">
          <button className="btn" onClick={() => logout()}>
            Logout
          </button>
        </div>
      </div>
      <main>
        <div className="header">
          <div className="note-info">
            <h2>
              {selectedNote ? `Editing ${selectedNote.title}` : "Select a note"}
            </h2>
            {lastUpdatedFormatted && <p>Edited {lastUpdatedFormatted}</p>}
          </div>
          <div className="actions">
            {useSocket ? (
              <button className="btn" onClick={toggleSocket}>
                Live Updates ON
              </button>
            ) : (
              <button className="btn" onClick={toggleSocket}>
                Live Updates OFF
              </button>
            )}
            {selectedNote && (
              <button className="btn btn-danger" onClick={handleDeleteNote}>
                Delete
              </button>
            )}
          </div>
        </div>
        <div className="document-area">
          {selectedNote && <NoteEditor note={selectedNote} />}
        </div>
      </main>
    </div>
  );
}
