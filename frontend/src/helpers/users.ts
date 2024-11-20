import { RegisterRequest, LoginRequest, UserType, ApiResponse } from "../types";

const USERS_ENDPOINT = import.meta.env.VITE_USERS_ENDPOINT as string;

export async function registerUser(
  user: RegisterRequest
): Promise<ApiResponse<number>> {
  const response = await fetch(`${USERS_ENDPOINT}/register`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
  });

  return response.json() as Promise<ApiResponse<number>>;
}

export async function loginUser(
  user: LoginRequest
): Promise<ApiResponse<UserType>> {
  const response = await fetch(`${USERS_ENDPOINT}/login`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(user),
  });

  return response.json() as Promise<ApiResponse<UserType>>;
}

export async function getUserById(id: number): Promise<ApiResponse<UserType>> {
  const response = await fetch(`${USERS_ENDPOINT}/${id}`);

  return response.json() as Promise<ApiResponse<UserType>>;
}
