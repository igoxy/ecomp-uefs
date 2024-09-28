module get_val_tot(clk, rst ,next_state , val_tot, got_coin, state_now);
	input clk, rst ,got_coin;
	input [3:0] next_state;
	
	parameter 
	penny0 = 4'b0000,
	penny0_25 = 4'b0001 ,
	penny0_50 = 4'b0010,
	penny0_75 = 4'b0011,
	penny1 = 4'b0100,
	penny1_25 = 4'b0101,
	penny1_50 = 4'b0110,
	penny1_75 = 4'b0111,
	penny2 = 4'b1000,
	penny_invalid = 4'b1001,
	wait_pulse_down = 4'b1111;
	
	
	output reg [3:0] val_tot, state_now;
	reg [3:0] state;

	
	initial begin
		state <= penny0;
		val_tot = 0;
		state_now <= 0;
		
	end
	
	always@(posedge clk, negedge rst) begin
		
		if(rst == 0) state <= penny0;
		
		else begin
			case(state)
		
				penny0: begin
				
					if(got_coin) begin
						
						state <= wait_pulse_down;
						
					end
					
					else state <= penny0;	
				end 
				
				wait_pulse_down: begin
					if(got_coin) state <= wait_pulse_down;
					
					else begin 
						case(next_state)
							penny0: state <=  penny0;
							penny0_25: state <=  penny0_25;
							penny0_50: state <=  penny0_50;
							penny0_75: state <=  penny0_75;
							penny1: state <=  penny1;
							penny1_25:  state <=  penny1_25;
							penny1_50:  state <=  penny1_50;
							penny1_75:  state <=  penny1_75;
							penny2:  state <=  penny2;
							default: state <= penny_invalid;
						endcase
					end
				end
				
				
				penny0_25: begin
				
					if(got_coin) begin
					
						
						state <= wait_pulse_down;
						
					end
					else state <= penny0_25;	
				end 
				
				penny0_50: begin
				
					if(got_coin) begin
					
						
						state <= wait_pulse_down;
						
					end
					else state <= penny0_50;	
				end 
				
				penny0_75: begin
				
					if(got_coin) begin
					
						
						state <= wait_pulse_down;
						
					end
					else state <= penny0_75;	
				end 
				
				penny1: begin
				
					if(got_coin) begin
					
						
						state <= wait_pulse_down;
						
					end
					else state <= penny1;	
				end 
				
				
				penny1_25: begin
				
					if(got_coin) begin
					
						
						state <= wait_pulse_down;
						
					end
					else state <= penny1_25;	
				end 
				
				penny1_50: begin
				
					if(got_coin) begin
					
						
						state <= wait_pulse_down;
						
					end
					else state <= penny1_50;	
				end 
				
				penny1_75: begin
				
					if(got_coin) begin
					
						
						state <= wait_pulse_down;
						
					end
					else state <= penny1_75;	
				end 
				
				penny2: begin
				
					if(got_coin) begin
					
						
						state <= wait_pulse_down;
						
					end
					else state <= penny2;	
				end 
				
				penny_invalid: begin
					
					if(got_coin) begin
					
						
						state <= wait_pulse_down;
						
					end
					else state <= penny_invalid;
				end
				
			endcase
		
		end
	
	
		
	end
	
	always@(state)begin
	
		case (state)
			penny0: val_tot = 0;
			penny0_25: val_tot = 1;
			penny0_50: val_tot = 2;
			penny0_75: val_tot = 3;
			penny1: val_tot = 4;
			penny1_25: val_tot = 5;
			penny1_50: val_tot = 6;
			penny1_75: val_tot = 7;
			penny2: val_tot = 8;
			penny_invalid: val_tot = 9;
			wait_pulse_down: val_tot = next_state;
		endcase
		state_now <= state;
	end
	
endmodule