import javax.swing.*;
import java.awt.*;

/**
 * Duckworth uses a simple formula to explain how achievement in Grit works:
 *
 * Talent × Effort = Skill
 *
 * Skill × Effort = Achievement
 *
 * Notice that Effort (Grit) appears twice. Talent helps you build a skill quickly,
 * but without the effort to use that skill over and over again, you won't actually achieve anything great.
 *
 * @author mariogianota (mariogianota@protonmail.com)
 */
public class GritDashboard extends JFrame implements GritModelListener{
    private GritModel model;

    private JSlider talentSlider = new JSlider(0, 100, 50);
    private JSlider effortSlider = new JSlider(0, 100, 50);


    private JLabel skillLabel = new JLabel();
    private JLabel achievementLabel = new JLabel();

    public GritDashboard(GritModel model) {
        this.model = model;
        model.setListener(this);

        setTitle("Grit Achievement Monitor");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 2, 10, 10)); //

        talentSlider.setPaintLabels(true);
        effortSlider.setPaintLabels(true);
        talentSlider.setMajorTickSpacing(25);
        talentSlider.setMinorTickSpacing(5);
        talentSlider.setPaintTicks(true);
        effortSlider.setMajorTickSpacing(25);
        effortSlider.setMinorTickSpacing(5);
        effortSlider.setPaintTicks(true);
        add(new JLabel("Talent"));
        add(talentSlider);
        add(new JLabel("Effort"));
        add(effortSlider);

        add(new JLabel("Skill:"));
        add(skillLabel);
        add(new JLabel("Achievement:"));
        add(achievementLabel);

        // 5. Work Button
        JButton workButton = new JButton("Calculate");
        workButton.addActionListener(e -> updateModel());
        add(workButton);

        setVisible(true);
    }

    private void updateModel() {
        model.setTalent(talentSlider.getValue()/100.0);
        model.setEffort(effortSlider.getValue()/100.0);
        model.calculate();

    }

    @Override
    public void modelChanged(GritModel model) {
        skillLabel.setText(""+model.getSkill());
        achievementLabel.setText(""+model.getAchievement());
    }
}