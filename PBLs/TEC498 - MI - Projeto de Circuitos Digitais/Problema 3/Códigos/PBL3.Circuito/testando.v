module testando(clk, y);
	input clk;
	output reg [0:4] y;
	reg [0:4] img [0:20];
	reg [0:4] ini_temp;
	reg [0:2] counter;
	integer i;
	
	
	initial begin

		
		img[0]  = 5'b01110;
		img[1]  = 5'b01010;
		img[2]  = 5'b01110;
		img[3]  = 5'b01010;
		img[4]  = 5'b01010;
		img[5]  = 5'b00000;
		img[6]  = 5'b00000;
		
		img[7]  = 5'b01110;
		img[8]  = 5'b01000;
		img[9]  = 5'b01000;
		img[10] = 5'b01000;
		img[11] = 5'b01110;
		img[12] = 5'b00000;
		img[13] = 5'b00000;
		
		img[14] = 5'b01110;
		img[15] = 5'b01010;
		img[16] = 5'b01100;
		img[17] = 5'b01010;
		img[18] = 5'b01110;
		img[19] = 5'b00000;
		img[20] = 5'b00000;
		
		counter = 0;
	end
	
	always @(posedge clk)begin
		
		ini_temp = img[0];
		
		for(i = 0; i < 6; i = i + 1) img[i] = img[i+1];
		
		img[6] = ini_temp;
		
		y <= img[counter];
		counter = counter + 1;
		
		if(counter ==  7) counter = 0;
	end
endmodule