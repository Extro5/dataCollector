package q3;

import com.google.gson.Gson;
import model.ActivityNameDiagram;
import model.MyChartData;
import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.List;

public class DataViewerGUI extends JFrame {

    private JButton button = new JButton("Build");

    private JTextField apiInput = new JTextField("", 12);

    private JLabel apiLabel = new JLabel("Api key:");

    private JTextField firstInput = new JTextField("", 12);

    private JLabel firstLabel = new JLabel("First parameter:");

    private JTextField secondInput = new JTextField("", 12);

    private JLabel secondLabel = new JLabel("Second parameter:");

    private JPanel chartPanel;

    private Container container;

    public DataViewerGUI() {
        super("Data viewer");
        this.setBounds(420, 160, 1000, 700);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.decode("#d2d2d2"));
        apiLabel.setFont(new Font("Arial", Font.BOLD, 14));
        firstLabel.setFont(new Font("Arial", Font.BOLD, 14));
        secondLabel.setFont(new Font("Arial", Font.BOLD, 14));


        container = this.getContentPane();
        container.setLayout((new FlowLayout()));

        container.add(apiLabel);
        container.add(apiInput);

        container.add(firstLabel);
        container.add(firstInput);

        container.add(secondLabel);
        container.add(secondInput);

        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    class ButtonEventListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            if (chartPanel != null) {
                getContentPane().remove(chartPanel);
            }

            String url = "http://localhost:8080/diagram/" + firstInput.getText() + "/" + secondInput.getText();

            URL obj;
            StringBuilder response = new StringBuilder();
            try {
                obj = new URL(url);

                HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                con.setRequestMethod("GET");
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);

                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;


                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //print result
                System.out.println(response.toString());

            } catch (Exception e1) {
                e1.printStackTrace();
            }

            Gson gson = new Gson();
            MyChartData myChartData = gson.fromJson(response.toString(), MyChartData.class);


            if (firstInput.getText().equals("users") && secondInput.getText().equals("activity_name")) {


                CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("").xAxisTitle("activities").yAxisTitle("visits").build();
                chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNW);
                chart.getStyler().setHasAnnotations(true);

                List<ActivityNameDiagram> activityNameDiagrams = myChartData.getActivityNameDiagram();

                List<String> activityName = new ArrayList<>();

                Map<String, Integer> maleActivityNameCount = new HashMap<>();
                Map<String, Integer> femaleActivityNameCount = new HashMap<>();

                for (ActivityNameDiagram activityNameDiagram : activityNameDiagrams) {
                    if (!activityName.contains(activityNameDiagram.getActivityName())) {
                        activityName.add(activityNameDiagram.getActivityName());
                    }
                }

                for (ActivityNameDiagram activityNameDiagram : activityNameDiagrams) {
                    if (activityNameDiagram.getGender().equals("MALE")) {
                        if (!maleActivityNameCount.containsKey(activityNameDiagram.getActivityName())) {
                            maleActivityNameCount.put(activityNameDiagram.getActivityName(), 1);
                        } else {
                            int count = maleActivityNameCount.get(activityNameDiagram.getActivityName());
                            maleActivityNameCount.replace(activityNameDiagram.getActivityName(), count, ++count);
                        }
                    } else if (activityNameDiagram.getGender().equals("FEMALE")) {
                        if (!femaleActivityNameCount.containsKey(activityNameDiagram.getActivityName())) {
                            femaleActivityNameCount.put(activityNameDiagram.getActivityName(), 1);
                        } else {
                            int count = femaleActivityNameCount.get(activityNameDiagram.getActivityName());
                            femaleActivityNameCount.replace(activityNameDiagram.getActivityName(), count, ++count);
                        }
                    }
                }
                System.out.println(maleActivityNameCount);
                System.out.println(femaleActivityNameCount);

                List<Integer> maleListCounts = new ArrayList<>(maleActivityNameCount.values());
                List<Integer> femaleListCounts = new ArrayList<>(femaleActivityNameCount.values());

                List<String> maleActivities = new ArrayList<>(maleActivityNameCount.keySet());
                List<String> femaleActivities = new ArrayList<>(femaleActivityNameCount.keySet());

                // Series
                chart.addSeries("Male", maleActivities, maleListCounts);
                chart.addSeries("Female", femaleActivities, femaleListCounts);


                // Schedule a job for the event-dispatching thread:
                // creating and showing this application's GUI.
                SwingUtilities.invokeLater(() -> {
                    chartPanel = new XChartPanel<>(chart);
                    container.add(chartPanel);

                });

            }  else {

                PieChart chart = new PieChartBuilder().width(800).height(600).title("").build();


                for (int i = 0; i < myChartData.getChartMap().size(); i++) {
                    chart.addSeries(myChartData.getDataList().get(i), Integer.parseInt(myChartData.getChartMap().get(myChartData.getDataList().get(i))));
                }

                SwingUtilities.invokeLater(() -> {
                    chartPanel = new XChartPanel<>(chart);
                    container.add(chartPanel);
                });

            }


            //update content
            SwingUtilities.updateComponentTreeUI(getContentPane());
            getContentPane().invalidate();
            getContentPane().validate();
            getContentPane().repaint();
        }
    }

    public static void main(String[] args) {
        DataViewerGUI app = new DataViewerGUI();
        app.setVisible(true);
    }
}