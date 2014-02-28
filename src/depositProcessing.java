
public class depositProcessing {
	
	static int newAccSum;
	static int _accSum;
	static byte _prc;
	static String _dateStr, _dateEnd;
	
	public static void newDeposit(int accSum, byte prc, String dateStr, String dateEnd){
		_accSum = accSum;
		_prc = prc;
		_dateStr = dateStr;
		_dateEnd = dateEnd;
	}
	public static int prcCalc(){
		newAccSum = _accSum * (1+_prc);
		return newAccSum;
	}
}
