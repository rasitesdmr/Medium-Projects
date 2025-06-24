'use strict';

var index_js = require('../common/index.cjs');
var index_js$1 = require('../ast/index.cjs');

const attribute = new Proxy(Object.freeze({}), {
  get: (_, key) => key,
});

class DotObject {}

class AttributesBase extends DotObject {
  #attrs = new Map();
  constructor(attributes) {
    super();
    if (attributes !== undefined) {
      this.apply(attributes);
    }
  }
  get values() {
    return Array.from(this.#attrs.entries());
  }
  get size() {
    return this.#attrs.size;
  }
  get(key) {
    return this.#attrs.get(key);
  }
  set(key, value) {
    if (value !== null && value !== undefined) {
      this.#attrs.set(key, value);
    }
  }
  delete(key) {
    this.#attrs.delete(key);
  }
  apply(attributes) {
    const entries = Array.isArray(attributes) ? attributes : Object.entries(attributes);
    for (const [key, value] of entries) {
      this.set(key, value);
    }
  }
  clear() {
    this.#attrs.clear();
  }
}

class AttributeList extends AttributesBase {
  $$kind;
  get $$type() {
    return 'AttributeList';
  }
  comment;
  constructor($$kind, attributes) {
    super(attributes);
    this.$$kind = $$kind;
  }
}

class GraphBase extends AttributesBase {
  #models = index_js.RootModelsContext;
  id;
  comment;
  attributes = Object.freeze({
    graph: new AttributeList('Graph'),
    edge: new AttributeList('Edge'),
    node: new AttributeList('Node'),
  });
  get nodes() {
    return Array.from(this.#objects.nodes.values());
  }
  get edges() {
    return Array.from(this.#objects.edges.values());
  }
  get subgraphs() {
    return Array.from(this.#objects.subgraphs.values());
  }
  #objects = {
    nodes: new Map(),
    edges: new Set(),
    subgraphs: new Set(),
  };
  with(models) {
    this.#models = index_js.createModelsContext(models);
  }
  addNode(node) {
    this.#objects.nodes.set(node.id, node);
  }
  addEdge(edge) {
    this.#objects.edges.add(edge);
  }
  addSubgraph(subgraph) {
    this.#objects.subgraphs.add(subgraph);
  }
  existNode(nodeId) {
    return this.#objects.nodes.has(nodeId);
  }
  existEdge(edge) {
    return this.#objects.edges.has(edge);
  }
  existSubgraph(subgraph) {
    return this.#objects.subgraphs.has(subgraph);
  }
  createSubgraph(...args) {
    const subgraph = new this.#models.Subgraph(...args);
    subgraph.with(this.#models);
    this.addSubgraph(subgraph);
    return subgraph;
  }
  removeNode(node) {
    this.#objects.nodes.delete(typeof node === 'string' ? node : node.id);
  }
  removeEdge(edge) {
    this.#objects.edges.delete(edge);
  }
  removeSubgraph(subgraph) {
    this.#objects.subgraphs.delete(subgraph);
  }
  createNode(id, attributes) {
    const node = new this.#models.Node(id, attributes);
    this.addNode(node);
    return node;
  }
  getSubgraph(id) {
    return Array.from(this.#objects.subgraphs.values()).find((subgraph) => subgraph.id === id);
  }
  getNode(id) {
    return this.#objects.nodes.get(id);
  }
  createEdge(targets, attributes) {
    const ts = targets.map((t) =>
      index_js.isNodeRefGroupLike(t) ? index_js.toNodeRefGroup(t) : index_js.toNodeRef(t),
    );
    const edge = new this.#models.Edge(ts, attributes);
    this.addEdge(edge);
    return edge;
  }
  subgraph(...args) {
    const id = args.find((arg) => typeof arg === 'string');
    const attributes = args.find((arg) => typeof arg === 'object' && arg !== null);
    const callback = args.find((arg) => typeof arg === 'function');
    const subgraph = id ? this.getSubgraph(id) ?? this.createSubgraph(id) : this.createSubgraph();
    if (attributes !== undefined) {
      subgraph.apply(attributes);
    }
    if (callback !== undefined) {
      callback(subgraph);
    }
    return subgraph;
  }
  node(firstArg, ...args) {
    if (typeof firstArg === 'string') {
      const id = firstArg;
      const attributes = args.find((arg) => typeof arg === 'object' && arg !== null);
      const callback = args.find((arg) => typeof arg === 'function');
      const node = this.getNode(id) ?? this.createNode(id);
      if (attributes !== undefined) {
        node.attributes.apply(attributes);
      }
      if (callback !== undefined) {
        callback(node);
      }
      return node;
    }
    if (typeof firstArg === 'object' && firstArg !== null) {
      this.attributes.node.apply(firstArg);
    }
  }
  edge(firstArg, ...args) {
    if (Array.isArray(firstArg)) {
      const targets = firstArg;
      const attributes = args.find((arg) => typeof arg === 'object');
      const callback = args.find((arg) => typeof arg === 'function');
      const edge = this.createEdge(targets, attributes);
      if (callback !== undefined) {
        callback(edge);
      }
      return edge;
    }
    if (typeof firstArg === 'object' && firstArg !== null) {
      this.attributes.edge.apply(firstArg);
    }
  }
  graph(attributes) {
    this.attributes.graph.apply(attributes);
  }
}

class RootGraph extends GraphBase {
  get $$type() {
    return 'Graph';
  }
  id;
  strict;
  constructor(...args) {
    super();
    this.id = args.find((arg) => typeof arg === 'string');
    this.strict = args.find((arg) => typeof arg === 'boolean') ?? false;
    const attributes = args.find((arg) => typeof arg === 'object' && arg !== null);
    if (attributes !== undefined) {
      this.apply(attributes);
    }
  }
}

class Digraph extends RootGraph {
  get directed() {
    return true;
  }
}

class Graph extends RootGraph {
  get directed() {
    return false;
  }
}

class Subgraph extends GraphBase {
  get $$type() {
    return 'Subgraph';
  }
  id;
  constructor(...args) {
    super();
    this.id = args.find((arg) => typeof arg === 'string');
    const attributes = args.find((arg) => typeof arg === 'object' && arg !== null);
    if (attributes !== undefined) {
      this.apply(attributes);
    }
  }
  isSubgraphCluster() {
    if (typeof this.id === 'string') {
      return this.id.startsWith('cluster');
    }
    return false;
  }
}

class AttributesGroup extends AttributesBase {
  comment;
}

class Node extends DotObject {
  id;
  get $$type() {
    return 'Node';
  }
  comment;
  attributes;
  constructor(id, attributes) {
    super();
    this.id = id;
    this.attributes = new AttributesGroup(attributes);
  }
  port(port) {
    if (typeof port === 'string') {
      return { id: this.id, port };
    }
    return { id: this.id, ...port };
  }
}

class Edge extends DotObject {
  targets;
  get $$type() {
    return 'Edge';
  }
  comment;
  attributes;
  constructor(targets, attributes) {
    super();
    this.targets = targets;
    if (targets.length < 2 && (index_js.isNodeRefLike(targets[0]) && index_js.isNodeRefLike(targets[1])) === false) {
      throw Error('The element of Edge target is missing or not satisfied as Edge target.');
    }
    this.attributes = new AttributesGroup(attributes);
  }
}

Object.assign(index_js.RootModelsContext, {
  Graph,
  Digraph,
  Subgraph,
  Node,
  Edge,
});

function ModelFactoryBuilder(directed, strictMode) {
  return (...args) => {
    const G = directed ? this.Digraph : this.Graph;
    const id = args.find((arg) => typeof arg === 'string');
    const attributes = args.find((arg) => typeof arg === 'object');
    const callback = args.find((arg) => typeof arg === 'function');
    const g = new G(id, strictMode, attributes);
    g.with(this);
    if (typeof callback === 'function') {
      callback(g);
    }
    return g;
  };
}
function createModelFactories(strict, context = index_js.RootModelsContext) {
  return Object.freeze({
    digraph: ModelFactoryBuilder.call(context, true, strict),
    graph: ModelFactoryBuilder.call(context, false, strict),
  });
}

const noStrict = createModelFactories(false);
const digraph = noStrict.digraph;
const graph = noStrict.graph;
const strict = createModelFactories(true);
function withContext(models) {
  const context = index_js.createModelsContext(models);
  return Object.freeze({
    ...createModelFactories(false, context),
    strict: createModelFactories(true, context),
  });
}

function toDot(model, options) {
  const ast = index_js$1.fromModel(model, options?.convert);
  return index_js$1.stringify(ast, options?.print);
}

function fromDot(dot, options) {
  const ast = index_js$1.parse(dot, options?.parse);
  if (
    Array.isArray(ast) ||
    ast.type === 'Attribute' ||
    ast.type === 'AttributeList' ||
    ast.type === 'Comment' ||
    ast.type === 'NodeRef' ||
    ast.type === 'NodeRefGroup' ||
    ast.type === 'Literal'
  ) {
    throw new Error();
  }
  return index_js$1.toModel(ast, options?.convert);
}

exports.AttributeList = AttributeList;
exports.AttributesBase = AttributesBase;
exports.AttributesGroup = AttributesGroup;
exports.Digraph = Digraph;
exports.DotObject = DotObject;
exports.Edge = Edge;
exports.Graph = Graph;
exports.GraphBase = GraphBase;
exports.Node = Node;
exports.RootGraph = RootGraph;
exports.Subgraph = Subgraph;
exports.attribute = attribute;
exports.digraph = digraph;
exports.fromDot = fromDot;
exports.graph = graph;
exports.strict = strict;
exports.toDot = toDot;
exports.withContext = withContext;
