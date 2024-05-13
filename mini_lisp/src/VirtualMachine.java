import java.util.Stack;

import enums.*;
import exceptions.*;

public class VirtualMachine {
    int programCounter;
    int stackPointer;
    int framePointer;
    int[] memory;
    final int maxMem = 100;
    final int startStack = 0;
    final int endStack = maxMem / 2;

    Stack<Integer> pc;
    Stack<Integer> fp;

    public VirtualMachine() {
        programCounter = 0;
        memory = new int[maxMem];
        stackPointer = startStack;
        framePointer = stackPointer;
        pc = new Stack<>();
        fp = new Stack<>();

    }

    public VmStatus run(int[] btc) {
        OpCode op;
        int a, b;
        try {
            while ((op = OpCode.values()[btc[programCounter++]]) != OpCode.HALT) {
                switch (op) {
                    case ADD:
                        push(pop() + pop());
                        break;
                    case SUB:
                        a = pop();
                        b = pop();
                        push(b - a);
                        break;
                    case MUL:
                        push(pop() * pop());
                        break;
                    case DIV:
                        a = pop();
                        b = pop();
                        if (a == 0) {
                            throw new VmException(
                                    "Zero division error", VmStatus.ZERO_DIVISION);
                        } else {
                            push(b / a);
                        }
                        break;
                    case EQ:
                        a = pop();
                        b = pop();
                        programCounter = (a == b) ? btc[programCounter] : ++programCounter;
                        break;
                    case GT:
                        a = pop();
                        b = pop();
                        programCounter = (b > a) ? btc[programCounter] : ++programCounter;
                        break;
                    case LT:
                        a = pop();
                        b = pop();
                        programCounter = (b < a) ? btc[programCounter] : ++programCounter;
                        break;
                    case PRINT:
                        System.out.println(peek(0));
                        break;
                    case STORE:
                        memory[btc[programCounter++]] = peek(0);
                        break;
                    case PUSHI:
                        push(btc[programCounter++]);
                        break;
                    case PUSHA:
                        push(memory[btc[programCounter++]]);
                        break;
                    case JMP:
                        programCounter = btc[programCounter];
                        break;
                    case CALL:
                        push(programCounter + 1);
                        framePointer = stackPointer;
                        push(framePointer);
                        fp.push(framePointer);
                        programCounter = btc[programCounter];
                        break;
                    case GET_LOCAL:
                        push(memory[framePointer + btc[programCounter]]);
                        programCounter++;
                        break;
                    case RETURN:
                        System.out.println("ret");
                        // framePointer = fp.pop();
                        framePointer = memory[framePointer + 1];
                        programCounter = memory[framePointer - 1];
                        break;
                    case NOP:
                        break;
                    default:
                        throw new VmException(
                                "unknow op code " + op, VmStatus.UNKNOW_OP_CODE);
                }
            }
        } catch (VmException e) {
            System.out.println(e.getMessage());
            return e.getStatus();
        }
        return VmStatus.SUCCES;
    }

    public void push(int n) throws VmException {
        if (stackPointer > endStack) {
            throw new VmException(
                    "Stack overflow", VmStatus.STACK_OVERFLOW);
        }
        memory[stackPointer++] = n;
    }

    public int pop() throws VmException {
        if (stackPointer <= startStack) {
            throw new VmException(
                    "Stack underflow", VmStatus.STACK_UNDERFLOW);
        }
        return memory[--stackPointer];
    }

    public int peek(int n) throws VmException {
        if (stackPointer <= startStack) {
            throw new VmException(
                    "Segmentation fault", VmStatus.SEG_FAULT);
        }
        return memory[stackPointer - 1 - n];
    }

    public void debug() {
        for (int i = 1; i < memory.length - 1; i++) {
            if (i % 10 == 0) {
                System.out.println();
            } else {
                System.out.print(memory[i - 1] + " ");
            }
        }
    }
}