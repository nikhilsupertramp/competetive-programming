import csv
import random
import math


def spam(inp, outp):
    csvfile=open('SampleProblemTestIO.csv','w', newline='')
    obj=csv.writer(csvfile)
    ip = open(inp)
    op = open(outp)
    inputLines = ip.readlines()
    outputLines = op.readlines()
    n = int(inputLines[0])
    j = 1;
    for i in range(0, n):
        input = ""
        input = inputLines[j] + inputLines[j + 1] + inputLines[j + 2]
        j += 3
        output = outputLines[i]
        tuple = (input, output);
        obj.writerow(tuple)



    ip.close()
    op.close()


    '''
    definition = str(meaning[0].get_text())
    tuple = (word, definition);
    obj.writerow(tuple)
    #print("here" + tag.contents)
    '''




if __name__ == "__main__":
    #for i in range(x, y + 1):
    #file1.write("\n\n"+ TYPE+str(i)+"\n")
    InputPath = "SampleProblem1TestInputs.txt"
    OutputPath = "SampleProblem1TestOutputs.txt"
    spam(InputPath, OutputPath)
