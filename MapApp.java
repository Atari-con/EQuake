import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class MapApp {

    private JFrame frame;
    private JPanel mapPanel;
    private ArrayList<Marker> markers;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MapApp().createAndShowGUI());
    }

    public void createAndShowGUI() {
        frame = new JFrame("Almaty UI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        
        mapPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawMap(g);
                drawMarkers(g);
            }
        };
        
        frame.add(mapPanel, BorderLayout.CENTER);
        loadMarkers("locations.json");
        
        frame.setVisible(true);
    }

    private void drawMap(Graphics g) {
        
        Image mapImage = new ImageIcon("map.jpg").getImage();
        g.drawImage(mapImage, 0, 0, this);
    }

    private void drawMarkers(Graphics g) {
        if (markers != null) {
            for (Marker marker : markers) {
                g.setColor(Color.RED);
                g.fillOval(marker.getX(), marker.getY(), 10, 10);
            }
        }
    }

    private void loadMarkers(String filePath) {
        markers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            StringBuilder jsonContent = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonContent.append(line);
            }

            JSONArray jsonArray = new JSONArray(jsonContent.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                double lat = jsonObject.getDouble("lat");
                double lng = jsonObject.getDouble("lng");
               
                int x = (int) (lng * 10); 
                int y = (int) (lat * 10);
                markers.add(new Marker(x, y));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static class Marker {
        private int x, y;

        public Marker(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }
}
