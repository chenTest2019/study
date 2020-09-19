public class P1 {
	private long b = 0;
	
	public void set1() {
		b = 0;
	}
	
	public void set2() {
		b = -1;
	}
	
	public void check() {
		//System.out.println(b);
		//第1个线程修改b为-1 时 满足第1个条件
		//当第3个线程开始判断第2个条件时 此时第1个线程 又把b修改了为0 进入条件
		//主要if中的条件和set操作不是原子的 在判断b的值时 可以修改b的值
		if (0 != b && -1 != b) {
			System.err.println("Error");
			//System.out.println(b);
			System.exit(0);
		}
	 }
}

