import { useEffect, useState } from 'react';
import type { ChangeEvent, FormEvent } from 'react';
import { createLaunchsite, deleteLaunchsite, fetchLaunchsites } from './api';
import { Badge } from './components/ui/badge';
import { Button } from './components/ui/button';
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from './components/ui/card';
import { Input } from './components/ui/input';
import { Label } from './components/ui/label';
import { Textarea } from './components/ui/textarea';
import type { Launchsite, LaunchsitePayload } from './types';

const initialForm: LaunchsitePayload = {
  name: '',
  latitude: 0,
  longitude: 0,
  directionStart: 0,
  directionEnd: 0,
  info: '',
};

function App() {
  const [launchsites, setLaunchsites] = useState<Launchsite[]>([]);
  const [form, setForm] = useState<LaunchsitePayload>(initialForm);
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const loadLaunchsites = async () => {
    setLoading(true);
    try {
      const data = await fetchLaunchsites();
      setLaunchsites(data);
      setError(null);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to load launchsites');
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    void loadLaunchsites();
  }, []);

  const handleChange = (event: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = event.target;
    setForm((prev) => ({
      ...prev,
      [name]: ['latitude', 'longitude', 'directionStart', 'directionEnd'].includes(name)
        ? Number(value)
        : value,
    }));
  };

  const handleSubmit = async (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    setSaving(true);
    try {
      const created = await createLaunchsite(form);
      setLaunchsites((prev) => [...prev, created]);
      setForm(initialForm);
      setError(null);
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to create launchsite');
    } finally {
      setSaving(false);
    }
  };

  const handleDelete = async (id: string) => {
    try {
      await deleteLaunchsite(id);
      setLaunchsites((prev) => prev.filter((launchsite) => launchsite.id !== id));
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Failed to delete launchsite');
    }
  };

  return (
    <div className="min-h-screen bg-background text-foreground">
      <div className="mx-auto flex min-h-screen max-w-6xl">
        <aside className="hidden w-64 flex-col gap-6 border-r border-border bg-card/60 p-6 md:flex">
          <div>
            <p className="text-xs font-semibold uppercase tracking-[0.3em] text-muted-foreground">
              Navigation
            </p>
            <h2 className="mt-3 text-lg font-semibold">Flightready</h2>
          </div>
          <nav className="flex flex-col gap-2">
            <Button variant="secondary" className="justify-start" asChild>
              <a href="#">Launchsites</a>
            </Button>
          </nav>
          <Card className="border-dashed">
            <CardHeader>
              <CardTitle className="text-base">Status</CardTitle>
              <CardDescription>API connection health</CardDescription>
            </CardHeader>
            <CardContent className="flex items-center gap-2">
              <span className="h-2 w-2 rounded-full bg-emerald-400" />
              <span className="text-sm text-muted-foreground">
                {loading ? 'Syncing data…' : 'Live updates ready'}
              </span>
            </CardContent>
          </Card>
        </aside>

        <main className="flex-1 px-6 py-10">
          <div className="flex flex-col gap-8">
            <header className="flex flex-col gap-3">
              <Badge className="w-fit">Operations</Badge>
              <h1 className="text-3xl font-semibold tracking-tight">Launchsites</h1>
              <p className="text-muted-foreground">
                Track launchsite coordinates, launch directions, and mission notes.
              </p>
            </header>

            <Card>
              <CardHeader>
                <CardTitle>Add a launchsite</CardTitle>
                <CardDescription>Provide precise coordinates and heading ranges.</CardDescription>
              </CardHeader>
              <CardContent>
                <form onSubmit={handleSubmit} className="grid gap-6 lg:grid-cols-2">
                  <div className="space-y-2">
                    <Label htmlFor="name">Name</Label>
                    <Input
                      id="name"
                      name="name"
                      value={form.name}
                      onChange={handleChange}
                      required
                      placeholder="Cape Canaveral"
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="latitude">Latitude</Label>
                    <Input
                      id="latitude"
                      name="latitude"
                      type="number"
                      step="0.000001"
                      value={form.latitude}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="longitude">Longitude</Label>
                    <Input
                      id="longitude"
                      name="longitude"
                      type="number"
                      step="0.000001"
                      value={form.longitude}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="directionStart">Direction start</Label>
                    <Input
                      id="directionStart"
                      name="directionStart"
                      type="number"
                      step="0.1"
                      value={form.directionStart}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="space-y-2">
                    <Label htmlFor="directionEnd">Direction end</Label>
                    <Input
                      id="directionEnd"
                      name="directionEnd"
                      type="number"
                      step="0.1"
                      value={form.directionEnd}
                      onChange={handleChange}
                      required
                    />
                  </div>
                  <div className="space-y-2 lg:col-span-2">
                    <Label htmlFor="info">Info</Label>
                    <Textarea
                      id="info"
                      name="info"
                      value={form.info}
                      onChange={handleChange}
                      placeholder="Optional notes"
                      rows={3}
                    />
                  </div>
                  <div className="lg:col-span-2">
                    <Button type="submit" disabled={saving}>
                      {saving ? 'Saving…' : 'Create launchsite'}
                    </Button>
                  </div>
                </form>
              </CardContent>
            </Card>

            <Card>
              <CardHeader>
                <CardTitle>Launchsites</CardTitle>
                <CardDescription>Manage every active launch location.</CardDescription>
              </CardHeader>
              <CardContent>
                {loading ? (
                  <p className="text-sm text-muted-foreground">Loading launchsites…</p>
                ) : launchsites.length === 0 ? (
                  <p className="text-sm text-muted-foreground">
                    No launchsites yet. Add one above.
                  </p>
                ) : (
                  <ul className="flex flex-col gap-4">
                    {launchsites.map((launchsite) => (
                      <li
                        key={launchsite.id}
                        className="flex flex-col gap-4 rounded-lg border border-border bg-background/40 p-4 lg:flex-row lg:items-center lg:justify-between"
                      >
                        <div className="space-y-2">
                          <div className="flex flex-wrap items-center gap-2">
                            <h3 className="text-lg font-semibold">{launchsite.name}</h3>
                            <Badge variant="secondary">Active</Badge>
                          </div>
                          <p className="text-sm text-muted-foreground">
                            Lat {launchsite.latitude.toFixed(4)}, Lng{' '}
                            {launchsite.longitude.toFixed(4)}
                          </p>
                          <p className="text-sm text-muted-foreground">
                            Directions: {launchsite.directionStart}° → {launchsite.directionEnd}°
                          </p>
                          {launchsite.info ? (
                            <p className="text-sm text-muted-foreground">
                              {launchsite.info}
                            </p>
                          ) : null}
                        </div>
                        <Button
                          type="button"
                          variant="destructive"
                          onClick={() => handleDelete(launchsite.id)}
                        >
                          Delete
                        </Button>
                      </li>
                    ))}
                  </ul>
                )}
                {error ? (
                  <div className="mt-4 rounded-md border border-destructive/40 bg-destructive/10 p-3 text-sm text-destructive">
                    {error}
                  </div>
                ) : null}
              </CardContent>
            </Card>
          </div>
        </main>
      </div>
    </div>
  );
}

export default App;
