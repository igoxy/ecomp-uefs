module controlMatriz(clk, A, C , R, button, COLUNA, LINHA);
	input clk, A, R, C, button;
	output reg [0:4]COLUNA;
	output reg [0:6]LINHA;
	
	reg [0:4] img [0:20];
	reg [0:4] img_SE [0:6];
	reg [0:2] lin_counter;
	reg [25:0] clk_Counter;
	reg [0:4] img_ini;
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
		img[18] = 5'b01010;
		img[19] = 5'b00000;
		img[20] = 5'b00000;
		
	
		
		clk_Counter = 0;
		lin_counter = 0;
		
	end
	
	always @(posedge clk_Counter[25], posedge A,posedge R, posedge C) begin
		case ({A, C, R, clk_Counter[25]})
		
			4'b1000: begin
	
				img_SE[0]  = img[0];
				img_SE[1]  = img[1];
				img_SE[2]  = img[2];
				img_SE[3]  = img[3];
				img_SE[4]  = img[4];
				img_SE[5]  = img[5];
				img_SE[6]  = img[6];
				
				
			end
			
			4'b0100: begin
	
				img_SE[0]  = img[7];
				img_SE[1]  = img[8];
				img_SE[2]  = img[9];
				img_SE[3]  = img[10];
				img_SE[4]  = img[11];
				img_SE[5]  = img[12];
				img_SE[6]  = img[13];
	
			
			end
			
			4'b0010: begin
	
				img_SE[0]  = img[14];
				img_SE[1]  = img[15];
				img_SE[2]  = img[16];
				img_SE[3]  = img[17];
				img_SE[4]  = img[18];
				img_SE[5]  = img[19];
				img_SE[6]  = img[20];
				

			end
			
		endcase
		
		if(clk_Counter[25]) begin
			img_ini = img_SE[0];
			
			
			for(i = 0; i < 6; i = i + 1) begin
				img_SE[i] = img_SE[i+1];
			end
			
			img_SE[6] =  img_ini;
			
		end
	
	end
	
	always @(posedge clk) begin
			
			clk_Counter = clk_Counter + 1;
		
	end
	
	
	always @(posedge clk_Counter[15]) begin
	
		if(button) begin
			COLUNA <= img_SE[lin_counter];
			LINHA <= 7'b1111111;
			LINHA[lin_counter] <= 0;
			lin_counter = lin_counter+1;
			
			if(lin_counter == 7) begin
			
				
					
				lin_counter = 0;
				end

		end

	end
	
	
	
	
	

endmodule