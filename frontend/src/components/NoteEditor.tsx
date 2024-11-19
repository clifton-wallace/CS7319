import { NoteType } from "../types";
import { updateNote } from "../helpers/notes";
import { useDebounce } from "use-debounce";
import { useEffect, useState } from "react";
import useNotes from "../hooks/use-notes";

type EditNoteProps = {
  note: NoteType;
};

export default function NoteEditor({ note }: EditNoteProps) {
  const { updateNote: updateOurNote } = useNotes();
  const [title, setTitle] = useState(note.title);
  const [currentText, setCurrentText] = useState(note.content);
  const [debouncedText] = useDebounce(currentText, 1000);

  useEffect(() => {
    const sendUpdate = async () => {
      const result = await updateNote(note.id, {
        title: note.title,
        content: debouncedText,
      });
      updateOurNote(result.data);
      console.log("Note updated");
    };

    if (debouncedText !== note.content) {
      sendUpdate();
    }
  }, [debouncedText]);

  useEffect(() => {
    setTitle(note.title);
    setCurrentText(note.content);
  }, [note]);

  const handleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    setCurrentText(event.target.value);
  };

  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  const handleTitleEnter = async (
    event: React.KeyboardEvent<HTMLInputElement>
  ) => {
    if (event.key === "Enter") {
      const result = await updateNote(note.id, { title, content: currentText });
      updateOurNote(result.data);
      console.log("Note title updated");
    }
  };

  return (
    <div className="document">
      <input
        type="text"
        name="docTitle"
        id="docTitle"
        value={title}
        onChange={handleTitleChange}
        onKeyDown={handleTitleEnter}
        onBlur={() => setTitle(note.title)}
      />
      <textarea
        className="document-content"
        placeholder="Start typing..."
        rows={30}
        value={currentText}
        onChange={handleChange}
      ></textarea>
    </div>
  );
}
