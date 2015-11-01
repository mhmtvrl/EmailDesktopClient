import java.util.Vector;


public class MessageBox {
	private Vector<EMail> box;
	
	public MessageBox()
	{
		box = new Vector<EMail>();
		box.add(null);
	}
        
        public int getSize()
        {
            return box.size();
        }
	
	public void printMessageBox()
	{
		for(int i = 1; i < box.size(); i++)
		{
			System.out.println(i + "nd message");
			System.out.println(box.get(i));
		}
	}
	
	public void addMessageBox(EMail email)
	{
		box.add(email);
	}

	public Vector<EMail> getBox() {
		return box;
	}

	public void setBox(Vector<EMail> box) {
		this.box = box;
	}
	
	public EMail getMessage(int id)
	{
		return this.box.get(id);
	}

}
