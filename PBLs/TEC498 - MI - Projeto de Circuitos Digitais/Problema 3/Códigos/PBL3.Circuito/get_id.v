module get_id
(
clk, rst,apt, time_max_id,
id_typed, state_now
);

	input clk,rst, apt, time_max_id;
	output reg id_typed;
	output reg [0:1] state_now;
	
	reg [0:1] state;

	
	
	parameter wait_id = 2'b00,
				 add_row = 2'b01,
				 add_col = 2'b10,
				 wait_button_down = 2'b11;
	
	initial begin
	
		state <= wait_id;
		id_typed = 0;
		state_now <= 0;
		
	end
				 
	
	always@(posedge clk, posedge time_max_id, negedge rst) begin
	
		if(time_max_id == 1 || rst == 0) begin
			state <= wait_id;
			id_typed = 0;
		end
		
		else begin
		
			case(state)
		
				wait_id: begin
				
					if(apt) begin
				
						state <= add_row;
						
					end
					else state <= wait_id;
						
						
				end
				
				add_row: begin
					if(apt) state <= add_row;
					else state <= wait_button_down;
							
				end
				
				wait_button_down: begin
				
					if(!apt) state <= wait_button_down;
						
					else state <= add_col;
				end
				
				add_col:begin
					
					if(apt) state <= add_col;
					
					else state <= wait_id;
		
					id_typed = 1;
					
								
				end	
			endcase
			
		end
	
		
	end
	
	always @(state) state_now <=  state;
endmodule