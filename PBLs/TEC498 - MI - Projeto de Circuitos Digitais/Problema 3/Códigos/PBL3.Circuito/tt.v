module tt(a,b,c, x,y,saldo);
input [0:3] x;
input  a,b,c;
output reg [0:3] y;
output reg [0:3]  saldo;
parameter produtoInvalido = 0;
reg [0:3] refri;
reg [0:3] sal;
reg [0:3] suco;
reg [0:3] produto;
reg [0:3] counter;

reg [0:1] m1;
reg [0:1] m2;
reg [0:1] m3;




	initial begin
		refri = 4'b1111;
		sal   = 4'b1110;
		suco  = 4'b1100;
		m1 = 2'b01;
		m2 = 2'b10;
		m3 = 2'b11;
		counter = 0;
		
	end
	
	always @(x) begin
	
		case(x)
		
			4'b1111: begin
				produto = refri;
			end
			4'b1110: begin
				produto = sal;
			end
			4'b1100: begin
				produto = suco;
			end
			default:
				produto = produtoInvalido;
			
		endcase
		y = produto;
	end
	
	
	always @(posedge a,posedge b,posedge c) begin
	
		case({a,b,c})
		
			3'b100: begin
				counter =  1;
				saldo  <=  counter;
			end
			
			3'b010: begin
				saldo  <=   2;
			end
			
			3'b001: begin
				saldo  <=   1;
			end
			
			
			
			
		endcase
		
	end
	
	
endmodule