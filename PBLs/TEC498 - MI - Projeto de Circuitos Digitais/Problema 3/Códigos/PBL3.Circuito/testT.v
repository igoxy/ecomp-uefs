module testT(clk, counter);
	input clk;
	output reg [29:0] counter;
	
	
	initial counter = 0;
	
	always@(posedge clk) begin
		counter = counter + 1;
		if(counter[27]) counter = 0;
	end
endmodule