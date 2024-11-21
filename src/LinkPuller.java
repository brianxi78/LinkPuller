import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class LinkPuller implements ActionListener {

    private JFrame frame;
    private JPanel panel;
    private JPanel grid;
    private JTextField text1;
    private JTextField text2;
    private JTextArea label1;
    private JButton button1;
    public String URL;
    public String search;


    public static void main(String[] args) {
        LinkPuller L = new LinkPuller();
    }

    public LinkPuller(){
        frame = new JFrame("Link Puller");
        panel = new JPanel(new BorderLayout());
        grid = new JPanel(new GridLayout(3,1));
        text1 = new JTextField("Link:");
        text2 = new JTextField("Search Term:");
        label1 = new JTextArea("Results");
        button1 = new JButton("Search");
        JScrollPane scroll = new JScrollPane(label1);
        button1.addActionListener(this);
        frame.add(panel);

        panel.add(button1, BorderLayout.EAST);
        panel.add(grid);
        grid.add(text1);
        grid.add(text2);
        grid.add(scroll);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);
        frame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        label1.setText("");
        if(e.getActionCommand() == "Search"){
            URL = text1.getText();
            search = text2.getText();
            Search();
        }
    }

    public void Search() {

        try {
            URL url = new URL(URL);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String line;
            int c = 1;
            while ( (line = reader.readLine()) != null ) {
                if (line.contains("https:") && line.contains(search)){
                    int start = line.indexOf("https:");
                    while (start != -1) {
                        int end = line.indexOf("\"", start);
                        String miniLine = line.substring(start, end);
                        if(miniLine.contains(search)){
                            System.out.println("link: " + miniLine);
                            label1.append(c + ". " + miniLine + "\n");
                            c++;
                        }
                        start = line.indexOf("https:", end);
                    }
                }
            }
            reader.close();
        } catch(Exception ex) {
            System.out.println(ex);
        }

    }
}
