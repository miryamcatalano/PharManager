package utilities.rendertable;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import utilities.GUItems;

public class CustomTable {
	
	private JTable table;
	private DefaultTableModel dm;

	public CustomTable(Object[][] data, String[] fields, int... editableColumns) {
		
		dm = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int col) {
				for(int c : editableColumns) {
					if(c == col)
						return true;
				}
				return false;
			}
		};
		
		boundTable(data, fields);
	}

	public CustomTable(Object[][] data, String[] fields, ArrayList<ArrayList<Integer>> notEditableRows, int... editableColumns) {
		
		dm = new DefaultTableModel() {
			@Override
			public boolean isCellEditable(int row, int col) {
				
				//se trovo una cella non modificabile restituisco direttamente false
				for(int i=0;i<notEditableRows.size();i++) {//righe
					if(row == i) {
						for(Integer cella : notEditableRows.get(i)) {
							if(col == cella) {
								return false;
							}
						}
					}
				}
				
				//verifico adesso che sia editabile, e qui ci arrivo naturalmente se le condizioni di prima non sono state rispettate
				for(int c : editableColumns) {
					if(c == col)
						return true;
				}
				
				
				return false;
			}
		};
		
		boundTable(data, fields);
	}
	
	private void boundTable(Object[][] data, String[] fields) {
		
		dm.setDataVector(data, fields);
		table = new JTable(dm);
		
		table.setFont(GUItems.getDefaultFont(25));
		table.setRowHeight(34);
		table.getTableHeader().setReorderingAllowed(false);
	}
	
	public String getCellValue(int row, int col) {
		return table.getModel().getValueAt(row, col).toString();
	}
	
	public JTable getTable() {
		return this.table;
	}
	
	public void setCellValue(String textCell, int row, int col) {
		table.getModel().setValueAt(textCell, row, col);
	}
	
	public void deleteRow(int row) {
		dm.removeRow(row);
	}
	
	public void setWidthPercentage(double... percentages) {
		
		final double factor = 10000;
		 
	    TableColumnModel model = table.getColumnModel();
	    for (int columnIndex = 0; columnIndex < percentages.length; columnIndex++) {
	        TableColumn column = model.getColumn(columnIndex);
	        column.setPreferredWidth((int) (percentages[columnIndex] * factor));
	    }
	}
}
