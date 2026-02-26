/** Duckworth uses a simple formula to explain how achievement in Grit works:
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
public class GritModel {
    private double talent;
    private double effort;
    private double skill;
    private double achievement;

    private GritModelListener listener;

    public GritModel() {
    }

    public void setListener(GritModelListener listener) {
        this.listener = listener;
    }
    /**
     * Simulates one 'time step' of work. 
     * Effort is the product of how hard you work and how long you stay focused.
     */
    public void calculate() {
        skill = talent * effort;
        achievement = skill * effort;
        modelChanged();
    }

    public double getSkill() { return skill; }

    public double getAchievement() { return achievement; }

    public void setTalent(double talent) {
        this.talent = talent;
        modelChanged();
    }
    public void setEffort(double effort) {
        this.effort = effort;
        modelChanged();
    }

    private void modelChanged() {
        listener.modelChanged(this);
    }
}