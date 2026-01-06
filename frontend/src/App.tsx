import { useEffect, useState } from 'react';
import type { ChangeEvent, FormEvent } from 'react';
import { createLaunchsite, deleteLaunchsite, fetchLaunchsites } from './api';
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
    <div className="app-shell">
      <aside className="sidebar">
        <div className="sidebar-header">Navigation</div>
        <nav className="sidebar-nav">
          <a className="nav-item active" href="#">
            Launchsites
          </a>
        </nav>
      </aside>

      <div className="app-content">
        <main className="page">
          <div className="page-header">
            <h1>Flightready</h1>
            <h2>Launchsites</h2>
            <p>Track launchsites with coordinates, directions, and notes.</p>
          </div>

          <section className="panel">
            <h3>Add a launchsite</h3>
            <form onSubmit={handleSubmit} className="form-grid">
              <label>
                Name
                <input
                  name="name"
                  value={form.name}
                  onChange={handleChange}
                  required
                  placeholder="Cape Canaveral"
                />
              </label>
              <label>
                Latitude
                <input
                  name="latitude"
                  type="number"
                  step="0.000001"
                  value={form.latitude}
                  onChange={handleChange}
                  required
                />
              </label>
              <label>
                Longitude
                <input
                  name="longitude"
                  type="number"
                  step="0.000001"
                  value={form.longitude}
                  onChange={handleChange}
                  required
                />
              </label>
              <label>
                Direction start
                <input
                  name="directionStart"
                  type="number"
                  step="0.1"
                  value={form.directionStart}
                  onChange={handleChange}
                  required
                />
              </label>
              <label>
                Direction end
                <input
                  name="directionEnd"
                  type="number"
                  step="0.1"
                  value={form.directionEnd}
                  onChange={handleChange}
                  required
                />
              </label>
              <label className="full">
                Info
                <textarea
                  name="info"
                  value={form.info}
                  onChange={handleChange}
                  placeholder="Optional notes"
                  rows={3}
                />
              </label>
              <button type="submit" disabled={saving}>
                {saving ? 'Saving...' : 'Create launchsite'}
              </button>
            </form>
          </section>

          <section className="panel">
            <h3>Launchsites</h3>
            {loading ? (
              <p>Loading launchsites...</p>
            ) : launchsites.length === 0 ? (
              <p>No launchsites yet. Add one above.</p>
            ) : (
              <ul className="list">
                {launchsites.map((launchsite) => (
                  <li key={launchsite.id}>
                    <div>
                      <strong>{launchsite.name}</strong>
                      <p>
                        Lat {launchsite.latitude.toFixed(4)}, Lng{' '}
                        {launchsite.longitude.toFixed(4)}
                      </p>
                      <p>
                        Directions: {launchsite.directionStart}° → {launchsite.directionEnd}°
                      </p>
                      {launchsite.info ? <p className="muted">{launchsite.info}</p> : null}
                    </div>
                    <button type="button" onClick={() => handleDelete(launchsite.id)}>
                      Delete
                    </button>
                  </li>
                ))}
              </ul>
            )}
            {error ? <p className="error">{error}</p> : null}
          </section>
        </main>
      </div>
    </div>
  );
}

export default App;
