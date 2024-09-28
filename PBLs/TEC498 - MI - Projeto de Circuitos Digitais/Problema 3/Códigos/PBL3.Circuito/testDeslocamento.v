module testDeslocamento(clk, y);
	input clk;
	output reg [0:3] y;
	
	
	always@ (posedge clk) y = + 1;
endmodule