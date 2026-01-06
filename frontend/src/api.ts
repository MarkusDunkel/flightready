import type { Launchsite, LaunchsitePayload } from './types';

const API_BASE = '/api/launchsites';

async function handleResponse<T>(response: Response): Promise<T> {
  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || 'Request failed');
  }
  return response.json() as Promise<T>;
}

export async function fetchLaunchsites(): Promise<Launchsite[]> {
  const response = await fetch(API_BASE);
  return handleResponse<Launchsite[]>(response);
}

export async function createLaunchsite(payload: LaunchsitePayload): Promise<Launchsite> {
  const response = await fetch(API_BASE, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(payload),
  });
  return handleResponse<Launchsite>(response);
}

export async function deleteLaunchsite(id: string): Promise<void> {
  const response = await fetch(`${API_BASE}/${id}`, { method: 'DELETE' });
  if (!response.ok) {
    const message = await response.text();
    throw new Error(message || 'Delete failed');
  }
}
