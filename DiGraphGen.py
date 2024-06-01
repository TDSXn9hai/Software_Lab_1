import matplotlib.pyplot as plt
import networkx as nx
import csv

G2 = nx.DiGraph()   # 创建图
# 读取csv文件
with open('data.csv', 'rt') as csvfile:
    reader = csv.DictReader(csvfile)
    nodes1 = [row['source'] for row in reader]
with open('data.csv', 'rt') as csvfile:
    reader = csv.DictReader(csvfile)
    nodes2 = [row['target'] for row in reader]
nodes1.extend(nodes2)       # 连接两个列表
nodes1 = list(set(nodes1))  # 去除重复元素

# 建立节点名和序号的对应关系
i = 0
for element in nodes1:
    i = i + 1
    G2.add_node(i, desc=element)

# 基于节点连接建立序号连接图
with open('data.csv', 'rt') as f:
    reader = csv.reader(f)

    next(reader)    # 去除首行

    HighLight = []  # 高亮路径
    for line in reader:
        for i in range(2):      # 只要line的前两个元素，即target名和source名
            j = 0
            for element in nodes1:   # 便利列表
                j = j + 1
                # print(line[i])
                if line[i] == element:
                    line[i] = j
                    # print("=========Changed=========")
                    break
        # print(line)
        if 'SIG' in line[2]:    # 是否被高亮
            line[2] = line[2].removesuffix('SIG')
            # print(line[2])
            HighLight.append(line)
        G2.add_edge(line[0], line[1], weight=line[2])

pos = nx.spring_layout(G2)  # 用 FR算法排列节点
nx.draw(G2, pos, with_labels=True, alpha=0.5)
node_labels = nx.get_node_attributes(G2, 'desc')
nx.draw_networkx_labels(G2, pos, labels=node_labels)
edge_labels = nx.get_edge_attributes(G2, 'weight')
nx.draw_networkx_edge_labels(G2, pos, edge_labels=edge_labels)

#   高亮处理
nx.draw_networkx_edges(G2, pos, edgelist=HighLight, edge_color='m', width=2)

plt.savefig('./DiGraph.pdf')
plt.show()
