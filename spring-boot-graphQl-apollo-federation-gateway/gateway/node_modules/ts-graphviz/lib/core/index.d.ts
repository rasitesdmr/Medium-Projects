import {
  Attribute,
  AttributeKey,
  Attributes,
  AttributesObject,
  AttributesEntities,
  AttributeListKind,
  AttributeListModel,
  AttributesGroupModel,
  GraphBaseModel,
  GraphCommonAttributes,
  NodeModel,
  EdgeModel,
  SubgraphModel,
  ModelsContext,
  SubgraphAttributesObject,
  NodeAttributesObject,
  EdgeTargetLikeTuple,
  EdgeAttributesObject,
  GraphAttributeKey,
  RootGraphModel,
  GraphAttributesObject,
  EdgeTargetTuple,
  EdgeAttributeKey,
  NodeAttributeKey,
  Port,
  ForwardRefNode,
  SubgraphAttributeKey,
  ClusterSubgraphAttributeKey,
  DotObjectModel,
} from '../common/index.js';
import { ConvertFromModelOptions, PrintOptions, ParseOptions, ConvertToModelOptions } from '../ast/index.js';

/**
 * @group Attribute
 *
 * @deprecated Use {@link Attribute.keys} instead.
 */
type AttributeKeyDict = Attribute.keys;
/**
 * @group Attribute
 */
declare const attribute: Attribute.keys;

/**
 * Base class for DOT objects.
 * @group Models
 */
declare abstract class DotObject {}

/**
 * Base class for DOT objects with attributes.
 * @group Models
 */
declare abstract class AttributesBase<T extends AttributeKey> extends DotObject implements Attributes<T> {
  #private;
  constructor(attributes?: AttributesObject<T>);
  get values(): ReadonlyArray<[T, Attribute<T>]>;
  get size(): number;
  get<K extends T>(key: K): Attribute<K> | undefined;
  set<K extends T>(key: K, value: Attribute<K>): void;
  delete(key: T): void;
  apply(attributes: AttributesObject<T> | AttributesEntities<T>): void;
  clear(): void;
}

/**
 * A set of attribute values for any object.
 * @group Models
 */
declare class AttributeList<K extends AttributeListKind, T extends AttributeKey = AttributeKey>
  extends AttributesBase<T>
  implements AttributeListModel<K, T>
{
  readonly $$kind: K;
  get $$type(): 'AttributeList';
  comment?: string;
  constructor($$kind: K, attributes?: AttributesObject<T>);
}

/**
 * A set of attribute values for any object.
 * @group Models
 */
declare class AttributesGroup<T extends AttributeKey = AttributeKey>
  extends AttributesBase<T>
  implements AttributesGroupModel<T>
{
  comment?: string;
}

/**
 * Base class for Graph objects.
 * @group Models
 */
declare abstract class GraphBase<T extends AttributeKey> extends AttributesBase<T> implements GraphBaseModel<T> {
  #private;
  readonly id?: string;
  comment?: string;
  readonly attributes: Readonly<GraphCommonAttributes>;
  get nodes(): ReadonlyArray<NodeModel>;
  get edges(): ReadonlyArray<EdgeModel>;
  get subgraphs(): ReadonlyArray<SubgraphModel>;
  with(models: Partial<ModelsContext>): void;
  addNode(node: NodeModel): void;
  addEdge(edge: EdgeModel): void;
  addSubgraph(subgraph: SubgraphModel): void;
  existNode(nodeId: string): boolean;
  existEdge(edge: EdgeModel): boolean;
  existSubgraph(subgraph: SubgraphModel): boolean;
  createSubgraph(id?: string, attributes?: SubgraphAttributesObject): SubgraphModel;
  createSubgraph(attributes?: SubgraphAttributesObject): SubgraphModel;
  removeNode(node: NodeModel | string): void;
  removeEdge(edge: EdgeModel): void;
  removeSubgraph(subgraph: SubgraphModel): void;
  createNode(id: string, attributes?: NodeAttributesObject): NodeModel;
  getSubgraph(id: string): SubgraphModel | undefined;
  getNode(id: string): NodeModel | undefined;
  createEdge(targets: EdgeTargetLikeTuple, attributes?: EdgeAttributesObject): EdgeModel;
  subgraph(id: string, callback?: (subgraph: SubgraphModel) => void): SubgraphModel;
  subgraph(
    id: string,
    attributes: SubgraphAttributesObject,
    callback?: (subgraph: SubgraphModel) => void,
  ): SubgraphModel;
  subgraph(attributes: SubgraphAttributesObject, callback?: (subgraph: SubgraphModel) => void): SubgraphModel;
  subgraph(callback?: (subgraph: SubgraphModel) => void): SubgraphModel;
  node(id: string, callback?: (node: NodeModel) => void): NodeModel;
  node(id: string, attributes: NodeAttributesObject, callback?: (node: NodeModel) => void): NodeModel;
  node(attributes: NodeAttributesObject): void;
  edge(targets: EdgeTargetLikeTuple, callback?: (edge: EdgeModel) => void): EdgeModel;
  edge(targets: EdgeTargetLikeTuple, attributes: EdgeAttributesObject, callback?: (edge: EdgeModel) => void): EdgeModel;
  edge(attributes: EdgeAttributesObject): void;
  graph(attributes: SubgraphAttributesObject): void;
}

/**
 * Base class representing a root graph(digraph, graph).
 * @group Models
 */
declare abstract class RootGraph extends GraphBase<GraphAttributeKey> implements RootGraphModel {
  get $$type(): 'Graph';
  readonly id?: string;
  abstract readonly directed: boolean;
  strict: boolean;
  constructor(id?: string, attributes?: GraphAttributesObject);
  constructor(id?: string, strict?: boolean, attributes?: GraphAttributesObject);
  constructor(strict?: boolean, attributes?: GraphAttributesObject);
  constructor(attributes?: GraphAttributesObject);
}

/**
 * DOT object class representing a digraph.
 * @group Models
 */
declare class Digraph extends RootGraph {
  get directed(): boolean;
}

/**
 * DOT object class representing a edge.
 * @group Models
 */
declare class Edge extends DotObject implements EdgeModel {
  readonly targets: EdgeTargetTuple;
  get $$type(): 'Edge';
  comment?: string;
  readonly attributes: AttributesGroupModel<EdgeAttributeKey>;
  constructor(targets: EdgeTargetTuple, attributes?: EdgeAttributesObject);
}

/**
 * DOT object class representing a graph.
 * @group Models
 */
declare class Graph extends RootGraph {
  get directed(): boolean;
}

/**
 * DOT object class representing a node.
 * @group Models
 */
declare class Node extends DotObject implements NodeModel {
  readonly id: string;
  get $$type(): 'Node';
  comment?: string;
  readonly attributes: AttributesGroup<NodeAttributeKey>;
  constructor(id: string, attributes?: NodeAttributesObject);
  port(port: string | Partial<Port>): ForwardRefNode;
}

/**
 * DOT object class representing a subgraph.
 * @group Models
 */
declare class Subgraph extends GraphBase<SubgraphAttributeKey | ClusterSubgraphAttributeKey> implements SubgraphModel {
  get $$type(): 'Subgraph';
  readonly id?: string;
  constructor(id?: string, attributes?: SubgraphAttributesObject);
  constructor(attributes?: SubgraphAttributesObject);
  isSubgraphCluster(): boolean;
}

/**
 * ModelFactory is an interface that provides a way to create a {@link RootGraphModel} object.
 *
 * @param id - Optional string parameter that specifies the id of the {@link RootGraphModel} object.
 * @param attributes - Optional GraphAttributesObject parameter that specifies the attributes of the {@link RootGraphModel} object.
 * @param callback - Optional callback function that takes a {@link RootGraphModel} object as a parameter.
 *
 * @returns {@link RootGraphModel} - Returns a {@link RootGraphModel} object.
 * @group Model Factory
 */
interface ModelFactory {
  (id?: string, attributes?: GraphAttributesObject, callback?: (g: RootGraphModel) => void): RootGraphModel;
  (attributes?: GraphAttributesObject, callback?: (g: RootGraphModel) => void): RootGraphModel;
  (id?: string, callback?: (g: RootGraphModel) => void): RootGraphModel;
  (callback?: (g: RootGraphModel) => void): RootGraphModel;
}
/**
 * @group Model Factory
 */
interface ModelFactories {
  /**
   * API for creating directional graph objects.
   */
  digraph: ModelFactory;
  /**
   * API for creating omnidirectional graph objects.
   */
  graph: ModelFactory;
}
/**
 * @group Model Factory
 */
interface ModelFactoriesWithStrict extends ModelFactories {
  strict: ModelFactories;
}

/**
 *  digraph is a factory for creating Digraph objects.
 * @group Model Factory
 */
declare const digraph: ModelFactory;
/**
 * graph is a factory for creating Graph objects.
 * @group Model Factory
 */
declare const graph: ModelFactory;
/**
 * Provides a strict mode API.
 * @group Model Factory
 */
declare const strict: ModelFactories;
/**
 * withContext creates a {@link ModelFactoriesWithStrict} object with the given context.
 *
 * @param models - An object containing the models to be used in the context.
 *
 * @returns A ModelFactoriesWithStrict object containing the factories. * @group Model Factory
 */
declare function withContext(models: Partial<ModelsContext>): ModelFactoriesWithStrict;

/**
 * This interface provides options for converting a model to DOT.
 * @group Convert Model to DOT
 * @alpha
 */
interface ToDotOptions {
  /**
   * Options for converting the model to DOT.
   */
  convert?: ConvertFromModelOptions;
  /**
   * Options for printing DOT.
   */
  print?: PrintOptions;
}
/**
 * Convert Model to DOT string.
 *
 * @group Convert Model to DOT
 *
 * @param model Dot Object Model, like {@link Digraph}, {@link Graph}, {@link Subgraph}, {@link Node}, and {@link Edge}
 * @param options Optional options for the conversion.
 * @returns DOT string
 */
declare function toDot(model: DotObjectModel, options?: ToDotOptions): string;

/**
 * This interface provides options for converting DOT to a model.
 * @group Convert DOT to Model
 * @alpha
 */
interface FromDotOptions<T extends 'Dot' | 'Graph' | 'Node' | 'Edge' | 'Subgraph'> {
  /**
   * Options for parsing DOT.
   */
  parse?: ParseOptions<T>;
  /**
   * Options for converting the parsed DOT to a model.
   */
  convert?: ConvertToModelOptions;
}
/**
 * fromDot is a function that converts a DOT string to a model.
 *
 * @group Convert DOT to Model
 *
 * @param dot The DOT string to convert.
 * @param options Options for converting the DOT string to a model.
 * @returns A model of type {@link RootGraphModel}, {@link NodeModel}, {@link EdgeModel}, or {@link SubgraphModel},
 * depending on the type specified in the options.
 * @beta
 */
declare function fromDot(dot: string, options?: FromDotOptions<'Dot' | 'Graph'>): RootGraphModel;
declare function fromDot(dot: string, options?: FromDotOptions<'Node'>): NodeModel;
declare function fromDot(dot: string, options?: FromDotOptions<'Edge'>): EdgeModel;
declare function fromDot(dot: string, options?: FromDotOptions<'Subgraph'>): SubgraphModel;

export {
  AttributeKeyDict,
  AttributeList,
  AttributesBase,
  AttributesGroup,
  Digraph,
  DotObject,
  Edge,
  FromDotOptions,
  Graph,
  GraphBase,
  ModelFactories,
  ModelFactoriesWithStrict,
  ModelFactory,
  Node,
  RootGraph,
  Subgraph,
  ToDotOptions,
  attribute,
  digraph,
  fromDot,
  graph,
  strict,
  toDot,
  withContext,
};
