import { useEffect, useState } from "react";

function App() {
  const [clusterData, setClusterData] = useState<any>(null);
  const [loading, setLoading] = useState(false);

  const fetchClusterMetrics = async () => {
    try {
      setLoading(true);
      const response = await fetch("/api/realtime/cluster");
      const json = await response.json();
      setClusterData(json);
    } catch (error) {
      console.error("Error fetching cluster metrics:", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchClusterMetrics();
  }, []);

  return (
    <div style={{ padding: "20px", fontFamily: "sans-serif" }}>
      <h1>Exec Dashboard Frontend</h1>

      {loading && <p>Loading metrics...</p>}

      {!loading && clusterData && (
        <pre
          style={{
            background: "#f5f5f5",
            padding: "10px",
            borderRadius: "6px",
            overflowX: "auto",
          }}
        >
          {JSON.stringify(clusterData, null, 2)}
        </pre>
      )}

      <button
        onClick={fetchClusterMetrics}
        style={{
          marginTop: "20px",
          padding: "10px 20px",
          cursor: "pointer",
          borderRadius: "8px",
          border: "1px solid #333",
        }}
      >
        Refresh
      </button>
    </div>
  );
}

export default App;