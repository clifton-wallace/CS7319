import { ApiResponse, CreateNoteRequest, NoteType } from "../types";

const NOTES_ENDPOINT = import.meta.env.VITE_NOTES_ENDPOINT as string;

export async function getNotes(): Promise<ApiResponse<NoteType[]>> {
  const response = await fetch(NOTES_ENDPOINT + "/");

  return response.json() as Promise<ApiResponse<NoteType[]>>;
}

export async function createNote(
  note: CreateNoteRequest
): Promise<ApiResponse<NoteType>> {
  const response = await fetch(`${NOTES_ENDPOINT}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(note),
  });

  return response.json() as Promise<ApiResponse<NoteType>>;
}

export async function updateNote(
  noteId: string,
  note: CreateNoteRequest
): Promise<ApiResponse<NoteType>> {
  const response = await fetch(`${NOTES_ENDPOINT}/${noteId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(note),
  });

  return response.json() as Promise<ApiResponse<NoteType>>;
}

export async function deleteNote(
  noteId: string
): Promise<ApiResponse<NoteType>> {
  const response = await fetch(`${NOTES_ENDPOINT}/${noteId}`, {
    method: "DELETE",
  });

  return response.json() as Promise<ApiResponse<NoteType>>;
}
