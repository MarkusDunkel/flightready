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
import './App.scss';

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
    <div className="app">
      <div className="app__layout">
        <aside className="app__sidebar">
          <div className="app__sidebar-header">
            <p className="app__sidebar-label">Navigation</p>
            <h2 className="app__sidebar-title">Flightready</h2>
          </div>
          <nav className="app__nav">
            <Button variant="secondary" className="app__nav-button" asChild>
              <a href="#">Launchsites</a>
            </Button>
          </nav>
          <Card className="app__status-card">
            <CardHeader>
              <CardTitle className="app__status-title">Status</CardTitle>
              <CardDescription>API connection health</CardDescription>
            </CardHeader>
            <CardContent className="app__status-content">
              <span className="app__status-indicator" />
              <span className="app__status-text">
                {loading ? 'Syncing data…' : 'Live updates ready'}
              </span>
            </CardContent>
          </Card>
        </aside>

        <main className="app__content">
          <div className="app__content-inner">
            <header className="app__page-header">
              <Badge className="app__page-badge">Operations</Badge>
              <h1 className="app__page-title">Launchsites</h1>
              <p className="app__page-subtitle">
                Track launchsite coordinates, launch directions, and mission notes.
              </p>
            </header>

            <Card>
              <CardHeader>
                <CardTitle>Add a launchsite</CardTitle>
                <CardDescription>Provide precise coordinates and heading ranges.</CardDescription>
              </CardHeader>
              <CardContent>
                <form onSubmit={handleSubmit} className="app__form">
                  <div className="app__form-field">
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
                  <div className="app__form-field">
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
                  <div className="app__form-field">
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
                  <div className="app__form-field">
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
                  <div className="app__form-field">
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
                  <div className="app__form-field app__form-field--full">
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
                  <div className="app__form-actions">
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
                  <p className="app__muted-text">Loading launchsites…</p>
                ) : launchsites.length === 0 ? (
                  <p className="app__muted-text">No launchsites yet. Add one above.</p>
                ) : (
                  <ul className="app__list">
                    {launchsites.map((launchsite) => (
                      <li key={launchsite.id} className="app__list-item">
                        <div className="app__list-body">
                          <div className="app__list-heading">
                            <h3 className="app__list-title">{launchsite.name}</h3>
                            <Badge variant="secondary">Active</Badge>
                          </div>
                          <p className="app__list-meta">
                            Lat {launchsite.latitude.toFixed(4)}, Lng{' '}
                            {launchsite.longitude.toFixed(4)}
                          </p>
                          <p className="app__list-meta">
                            Directions: {launchsite.directionStart}° → {launchsite.directionEnd}°
                          </p>
                          {launchsite.info ? (
                            <p className="app__list-info">{launchsite.info}</p>
                          ) : null}
                        </div>
                        <div className="app__list-actions">
                          <Button
                            type="button"
                            variant="destructive"
                            onClick={() => handleDelete(launchsite.id)}
                          >
                            Delete
                          </Button>
                        </div>
                      </li>
                    ))}
                  </ul>
                )}
                {error ? <div className="app__error">{error}</div> : null}
              </CardContent>
            </Card>
          </div>
        </main>
      </div>
    </div>
  );
}

export default App;
