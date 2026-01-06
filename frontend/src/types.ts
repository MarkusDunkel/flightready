export interface Launchsite {
  id: string;
  name: string;
  latitude: number;
  longitude: number;
  directionStart: number;
  directionEnd: number;
  info?: string | null;
}

export interface LaunchsitePayload {
  name: string;
  latitude: number;
  longitude: number;
  directionStart: number;
  directionEnd: number;
  info?: string;
}
