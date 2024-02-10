	LDA a
	SUB b
	STA a
loop LDA a
	SUB b
	STA a	
	LDA res
	ADD one
	STA res
	LDA a
	BRP loop
end	LDA res
	OUT 
    HLT

a       DAT 10
b       DAT 3
one     DAT 1 
res 	DAT 0
rest    DAT 0