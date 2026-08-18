import "./App.css";
import { useState } from "react";

const campusMapImage = "/lpu-campus-map.jpg";

const locations = [
  "LIM",
  "Campus Cafe",
  "Auditorium",
  "LIT Engineering",
  "LIT Pharmacy",
  "LIT Architecture",
  "Shri Baldev Raj Mittal Hospital",
  "Girls Hostel 1",
  "Girls Hostel 2",
  "Girls Hostel 3",
  "Girls Hostel 4",
  "LIT Polytechnic",
  "Business Block",
  "Lovely Mall",
  "Hotel Mgt",
  "Mall - II",
  "Education",
  "LSB",
  "Girl Hostel 5",
  "Girl Hostel 6",
  "Chancellor Office",
  "Administrative Block",
  "Engineering",
  "STP",
  "Store",
  "Staff Residence",
  "Boys Hostel 1",
  "Boys Hostel 2",
  "Boys Hostel 3",
  "Boys Hostel 4",
  "Boys Hostel 5",
  "Boys Hostel 6",
  "Academic Block 1",
  "Academic Block 2",
  "Academic Block 3"
];

const locationPositions = {
  LIM: { x: 34, y: 55 },
  "Campus Cafe": { x: 44, y: 50 },
  Auditorium: { x: 57, y: 42 },
  "LIT Engineering": { x: 24, y: 66 },
  "LIT Pharmacy": { x: 31, y: 72 },
  "LIT Architecture": { x: 38, y: 80 },
  "Shri Baldev Raj Mittal Hospital": { x: 22, y: 30 },
  "Girls Hostel 1": { x: 80, y: 78 },
  "Girls Hostel 2": { x: 85, y: 72 },
  "Girls Hostel 3": { x: 87, y: 65 },
  "Girls Hostel 4": { x: 88, y: 58 },
  "LIT Polytechnic": { x: 70, y: 74 },
  "Business Block": { x: 58, y: 36 },
  "Lovely Mall": { x: 53, y: 51 },
  "Hotel Mgt": { x: 63, y: 54 },
  "Mall - II": { x: 67, y: 61 },
  Education: { x: 46, y: 61 },
  LSB: { x: 65, y: 45 },
  "Girl Hostel 5": { x: 75, y: 68 },
  "Girl Hostel 6": { x: 79, y: 62 },
  "Chancellor Office": { x: 73, y: 35 },
  "Administrative Block": { x: 68, y: 29 },
  Engineering: { x: 49, y: 72 },
  STP: { x: 36, y: 90 },
  Store: { x: 43, y: 88 },
  "Staff Residence": { x: 52, y: 95 },
  "Boys Hostel 1": { x: 14, y: 82 },
  "Boys Hostel 2": { x: 18, y: 76 },
  "Boys Hostel 3": { x: 22, y: 70 },
  "Boys Hostel 4": { x: 28, y: 64 },
  "Boys Hostel 5": { x: 33, y: 58 },
  "Boys Hostel 6": { x: 39, y: 52 },
  "Academic Block 1": { x: 12, y: 43 },
  "Academic Block 2": { x: 17, y: 35 },
  "Academic Block 3": { x: 25, y: 29 }
};

function App() {
  const [fromLocation, setFromLocation] = useState("");
  const [toLocation, setToLocation] = useState("");
  const [routeInfo, setRouteInfo] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const getLocationId = (locationName) => {
    const locationMap = {
      LIM: 1,
      "Campus Cafe": 2,
      Auditorium: 3,
      "LIT Engineering": 4,
      "LIT Pharmacy": 5,
      "LIT Architecture": 6,
      "Shri Baldev Raj Mittal Hospital": 8,
      "Girls Hostel 1": 9,
      "Girls Hostel 2": 10,
      "Girls Hostel 3": 11,
      "Girls Hostel 4": 12,
      "LIT Polytechnic": 13,
      "Business Block": 14,
      "Lovely Mall": 15,
      "Hotel Mgt": 16,
      "Mall - II": 17,
      Education: 18,
      LSB: 20,
      "Girl Hostel 5": 21,
      "Girl Hostel 6": 22,
      "Chancellor Office": 30,
      "Administrative Block": 31,
      Engineering: 25,
      STP: 39,
      Store: 40,
      "Staff Residence": 41,
      "Boys Hostel 1": 43,
      "Boys Hostel 2": 44,
      "Boys Hostel 3": 46,
      "Boys Hostel 4": 47,
      "Boys Hostel 5": 51,
      "Boys Hostel 6": 52,
      "Academic Block 1": 53,
      "Academic Block 2": 54,
      "Academic Block 3": 55
    };
    return locationMap[locationName];
  };

  const routeMarkers = [];

  const handleFindRoute = async () => {
    if (!fromLocation || !toLocation) {
      setError("Please select both starting point and destination");
      return;
    }

    if (fromLocation === toLocation) {
      setError("Starting point and destination cannot be the same");
      return;
    }

    setLoading(true);
    setError("");
    setRouteInfo(null);

    try {
      const fromId = getLocationId(fromLocation);
      const toId = getLocationId(toLocation);

      if (!fromId || !toId) {
        throw new Error("Selected locations are not available in the campus map.");
      }

      const response = await fetch(
        `http://localhost:8080/api/route?from=${fromId}&to=${toId}`
      );

      if (!response.ok) {
        let backendMessage = "No route found for the selected locations.";

        try {
          const errorData = await response.json();
          if (errorData && errorData.error) {
            backendMessage = errorData.error;
          }
        } catch {
          // Ignore JSON parse issues and keep fallback message.
        }

        throw new Error(backendMessage);
      }

      const data = await response.json();
      setRouteInfo(data);
    } catch (err) {
      if (err instanceof TypeError) {
        setError("Failed to fetch route. Start the Java backend on port 8080 and try again.");
      } else {
        setError(err.message || "Error finding route. Make sure backend is running on port 8080");
      }
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app">
      <header className="header">
        <div>
          <h1>LPU Smart Campus Navigator</h1>
          <p>Find the shortest route across Lovely Professional University</p>
        </div>
      </header>

      <main className="main">
        <section className="search-section">
          <h2>Find Your Route</h2>

          <div className="route-inputs">
            <div className="input-group">
              <label>From</label>
              <select value={fromLocation} onChange={(e) => setFromLocation(e.target.value)}>
                <option value="">Select starting point</option>
                {locations.map((location) => (
                  <option key={location} value={location}>
                    {location}
                  </option>
                ))}
              </select>
            </div>

            <div className="swap">⇄</div>

            <div className="input-group">
              <label>To</label>
              <select value={toLocation} onChange={(e) => setToLocation(e.target.value)}>
                <option value="">Select destination</option>
                {locations.map((location) => (
                  <option key={location} value={location}>
                    {location}
                  </option>
                ))}
              </select>
            </div>

            <button className="route-button" onClick={handleFindRoute} disabled={loading}>
              {loading ? "Finding Route..." : "Find Route"}
            </button>
          </div>
        </section>

        <section className="content">
          <div className="map-section">
            <div className="section-title">
              <h2>Campus Map</h2>
            </div>

            <div className="map-canvas">
              <img src={campusMapImage} alt="LPU campus map" className="map-image" />

              {routeMarkers.map((marker, index) => {
                const isStart = index === 0;
                const isEnd = index === routeMarkers.length - 1;

                return (
                  <div
                    key={`${marker.name}-${index}`}
                    className={`map-marker ${isStart ? "start" : ""} ${isEnd ? "end" : ""}`}
                    style={{ left: `${marker.x}%`, top: `${marker.y}%` }}
                    title={marker.name}
                  >
                    <span>{isStart ? "S" : isEnd ? "D" : index + 1}</span>
                  </div>
                );
              })}
            </div>
          </div>

          <aside className="route-panel">
            <h2>Route Information</h2>

            {error && (
              <div className="error-message">
                <span>⚠️</span>
                <p>{error}</p>
              </div>
            )}

            {routeInfo ? (
              <div className="route-details">
                <div className="route-header">
                  <p><strong>From:</strong> {routeInfo.source}</p>
                  <p><strong>To:</strong> {routeInfo.destination}</p>
                </div>

                <div className="route-stats">
                  <div className="stat">
                    <span>📍</span>
                    <div>
                      <h4>Distance</h4>
                      <p>{routeInfo.distance} meters</p>
                    </div>
                  </div>

                  <div className="stat">
                    <span>⏱️</span>
                    <div>
                      <h4>Walking Time</h4>
                      <p>~{Math.floor(routeInfo.distance / 2)} seconds</p>
                    </div>
                  </div>
                </div>

                <div className="route-path">
                  <h4>Route Path:</h4>
                  <ol>
                    {routeInfo.path && routeInfo.path.map((location, index) => (
                      <li key={`${location}-${index}`}>{location}</li>
                    ))}
                  </ol>
                </div>
              </div>
            ) : (
              <div className="empty-route">
                <span>📍</span>
                <p>Select your starting point and destination to find the shortest route.</p>
              </div>
            )}
          </aside>
        </section>
      </main>
    </div>
  );
}

export default App;