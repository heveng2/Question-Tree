/**
 * Represents a single node in the question tree.
 * Each node stores either a question or an answer.
 */
 //@author Heven Gebrehiwot 
 
public class QuestionNode {
    String data;
    QuestionNode yes;
    QuestionNode no;

    /**
     * Constructs a leaf node representing an answer.
     * 
     * @param data The answer stored in this node.
     */
    public QuestionNode(String data) {
        this.data = data;
        this.yes = null;
        this.no = null;
    }

    /**
     * Constructs a question node with yes and no branches.
     * 
     * @param yes The subtree for a "yes" response.
     * @param no The subtree for a "no" response.
     * @param data The question stored in this node.
     */
    public QuestionNode(QuestionNode yes, QuestionNode no, String data) {
        this.data = data;
        this.yes = yes;
        this.no = no;
    }

    /**
     * Checks if the node is a leaf (an answer).
     * 
     * @return True if this is an answer node, false otherwise.
     */
    public boolean isLeaf() {
        return (yes == null && no == null);
    }
}
