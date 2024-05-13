import enums.*;

public class Main {
    public static void main(String[] args) throws Exception {
        int[] prog = {
                OpCode.PUSHI.ordinal(),
                5,
                OpCode.CALL.ordinal(),
                5,
                OpCode.HALT.ordinal(),
                OpCode.GET_LOCAL.ordinal(),
                -2,
                OpCode.PRINT.ordinal(),
                OpCode.PUSHI.ordinal(),
                10,
                OpCode.EQ.ordinal(),
                19,
                OpCode.GET_LOCAL.ordinal(),
                -2,
                OpCode.PUSHI.ordinal(),
                1,
                OpCode.ADD.ordinal(),
                OpCode.CALL.ordinal(),
                5,
                OpCode.RETURN.ordinal(),

        };
        VirtualMachine vm = new VirtualMachine();
        VmStatus status = vm.run(prog);
        System.out.println("Vm status " + status);
        System.out.println();
        // vm.debug();

    }
}
