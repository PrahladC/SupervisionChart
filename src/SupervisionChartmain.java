import javax.swing.SwingUtilities;


public class SupervisionChartmain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		  SwingUtilities.invokeLater(new Runnable()
	        {
				public void run() {
					supchartview View = new supchartview();
					supchartmodel Model = new supchartmodel();
					supchartcontroller Controller = new supchartcontroller(Model, View);
					Controller.control();
					View.setVisible(true);
//					View.setLocationRelativeTo(null);
					View.setResizable(false);
				}

	        });
	}

}
