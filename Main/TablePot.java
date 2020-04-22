/**
 * This class adds 
 */
package Main;

public class TablePot {
private int tablePot;
public TablePot()
{
}
public void increaseTablePot(int n)
{
	tablePot+=n;
}
public int getTablePot()
{
	return tablePot;
}
public int giveTablePot()
{
	int total=tablePot;
	tablePot=tablePot-tablePot;
	return total;
}
}
