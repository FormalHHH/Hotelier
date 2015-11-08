package co.jp.worksap.intern.global;

import java.util.Arrays;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

public class Table extends AbstractTableModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4746265560334629599L;
	public Table(String[] head, String[][] table) {
		content = new Vector<Vector<String>>();
		for (String[] line:table) {
			Vector<String> v = new Vector<String>();
			for (String unit:line) {
				v.add(unit);
			}
			content.add(v);
		}
		this.head = head;
	}
	
	public Table(String[] array) {
		content = new Vector<Vector<String>>();
		int width, height;
		for (width = 1; array[width] != null; ++width);
		--width;
		head = new String[width];
		for (int i = 1; i <= width; ++i) {
			head[i - 1] = array[i];
		}
		height = (array.length - (1 + width + 1)) / width;
		for (int i = 0; i < height; ++i) {
			Vector<String> v = new Vector<String>();
			for (int j = 0; j < width; ++j) {
				v.add(array[1 + width + 1 + i * width + j]);
			}
			content.add(v);
		}
	}
	
	public String[] getArrayForMessage() {
		String[] info = new String[1 + head.length + 1 + content.size() * head.length];
		info[0] = "SUCCESS";
		for (int i = 1; i <= head.length; ++i) {
			info[i] = head[i - 1];
		}
		info[head.length + 1] = null;
		for (int i = 0; i < content.size(); ++i) {
			for (int j = 0; j < head.length; ++j) {
				info[1 + head.length + 1 + i * head.length + j] = content.get(i).get(j);
			}
		}
		return info;
	}
	
	public String[][] getTable() {
		String[][] table = new String[content.size()][content.get(0).size()];
		for (int i = 0; i < content.size(); ++i) {
			for (int j = 0; j < content.get(0).size(); ++j) {
				table[i][j] = content.get(i).get(j);
			}
		}
		return table;
	}
	
	public String[] getHead() {
		return head;
	}
	
	public void setHead(String[] head) {
		this.head = head;
	}
	
	public void addRow(String[] row) {
		content.add(new Vector<String>(Arrays.asList(row)));
	}
	
	public void removeRow(int row) {
		content.remove(row);
	}

	public void removeRows(int row, int count) throws ArrayIndexOutOfBoundsException {
		if (row + count >= content.size())
			throw new ArrayIndexOutOfBoundsException();
		for (; count > 0; --count) {
			content.remove(row);
		}
	}

	@Override
	public int getRowCount() {
		return content.size();
	}

	@Override
	public int getColumnCount() {
		return head.length;
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		return content.get(rowIndex).get(columnIndex);
	}
	
	@Override
	public String getColumnName(int col) {
		return head[col];
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return false;
		}
		return true;
	}
	
	@Override
	public void setValueAt(Object aValue, int row, int column) {
		//if (aValue instanceof Integer) {
			content.get(row).set(column, aValue.toString());
		/*} else if (aValue instanceof String) {{
			content.get(row).set(column, aValue);
		}*/
		fireTableCellUpdated(row, column);
	}
	
	public void setValueAt(Object aValue, int row, int column, boolean annouance) {
		//if (aValue instanceof Integer) {
			content.get(row).set(column, aValue.toString());
		/*} else if (aValue instanceof String) {{
			content.get(row).set(column, aValue);
		}*/
		if (annouance) fireTableCellUpdated(row, column);
	}
	
	private Vector<Vector<String>> content = null;
	private String[] head;
}
