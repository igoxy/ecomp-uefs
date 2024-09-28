module add_id(clk , rst,st1, key_one ,key_two, key_three,key_four, row, col);
	input clk, rst ,key_one ,key_two, key_three,key_four;
	input [0:1]  st1;
	output reg 	[1:0] row, col;
	
	parameter add_row = 2'b01, add_col = 2'b10;

	initial begin
		row <= 0;
		col <= 0;
	end	
	
	always@(posedge clk, negedge rst) begin
	
		if(rst == 0) begin
			row <= 0;
			col <= 0;
		end
		else begin
			case(st1)
				add_row: begin
					case({key_one ,key_two, key_three,key_four})
								
									4'b1000: begin 
										 row <= 0;
								
									end
									
									4'b0100: begin 
										 row <= 1;
										
									end
									
									4'b0010: begin 
										 row <= 2;
										
									end
									
									4'b0001: begin 
										row <= 3;
										
									end
									
									
								
						endcase
				end
				
				add_col: begin
					case({key_one ,key_two, key_three,key_four})
									
										4'b1000: begin 
											 col <= 0;
									
										end
										
										4'b0100: begin 
											 col <= 1;
											
										end
										
										4'b0010: begin 
											 col <= 2;
											
										end
										
										4'b0001: begin 
											col <= 3;
											
										end
									
						endcase
				end
				
				
				
			endcase
		end
		
	end
	
endmodule