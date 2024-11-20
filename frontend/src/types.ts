export type NoteVersionType = {
  versionNumber: number;
  content: string;
  timestamp: string;
};

export type NoteType = {
  id: string;
  title: string;
  content: string;
  versions: NoteVersionType[];
  created: string;
  lastUpdated: string;
  createdByUserId: number;
  lastUpdatedByUserId: number;
};

export type CreateNoteRequest = {
  title: string;
  content: string;
  userId: number;
}

export type UserType = {
  id: number;
  email: string;
  password: string;
  role: "USER" | "ADMIN";
  createdAt: string;
  updatedAt: string;
}

export type LoginRequest = {
  emailAddress: string;
  password: string;
}

export type RegisterRequest = {
  email: string;
  password: string;
  role: "USER" | "ADMIN";
};

export type ApiResponse<T> = {
  success: boolean;
  message: string;
  data: T;
};

export type NotesBroadcast = {
  note: NoteType;
  action: "CREATE" | "UPDATE" | "DELETE";
}