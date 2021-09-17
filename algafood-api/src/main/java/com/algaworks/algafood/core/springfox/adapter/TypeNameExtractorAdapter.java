package com.algaworks.algafood.core.springfox.adapter;

/**
 * Workaround para resolver problemas de versão de dependência transitiva entre
 * Spring HATEOAS e Spring fox
 * https://github.com/springfox/springfox/issues/2932
 */

//@Primary
//@Component
//public class TypeNameExtractorAdapter extends TypeNameExtractor {
//	private final TypeResolver typeResolver;
//	private final PluginRegistry<TypeNameProviderPlugin, DocumentationType> typeNameProviders;
//	private final EnumTypeDeterminer enumTypeDeterminer;

//	@Autowired
//	public TypeNameExtractorAdapter(TypeResolver typeResolver,
//			@Qualifier("typeNameProviderPluginRegistry") PluginRegistry<TypeNameProviderPlugin, DocumentationType> typeNameProviders,
//			EnumTypeDeterminer enumTypeDeterminer) {
//		super(typeResolver, typeNameProviders, enumTypeDeterminer);
//		this.typeResolver = typeResolver;
//		this.typeNameProviders = typeNameProviders;
//		this.enumTypeDeterminer = enumTypeDeterminer;
//	}
//
//	@Override
//	public String typeName(ModelContext context) {
//		ResolvedType type = asResolved(context.getType());
//		if (isContainerType(type)) {
//			return containerType(type);
//		}
//		return innerTypeName(type, context);
//	}
//
//	private ResolvedType asResolved(Type type) {
//		return typeResolver.resolve(type);
//	}
//
//	private String genericTypeName(ResolvedType resolvedType, ModelContext context) {
//		Class<?> erasedType = resolvedType.getErasedType();
//		GenericTypeNamingStrategy namingStrategy = context.getGenericNamingStrategy();
//		ModelNameContext nameContext = new ModelNameContext(resolvedType.getErasedType(),
//				context.getDocumentationType());
//		String simpleName = fromNullable(typeNameFor(erasedType)).or(typeName(nameContext));
//		StringBuilder sb = new StringBuilder(String.format("%s%s", simpleName, namingStrategy.getOpenGeneric()));
//		boolean first = true;
//		for (int index = 0; index < erasedType.getTypeParameters().length; index++) {
//			ResolvedType typeParam = resolvedType.getTypeParameters().get(index);
//			if (first) {
//				sb.append(innerTypeName(typeParam, context));
//				first = false;
//			} else {
//				sb.append(String.format("%s%s", namingStrategy.getTypeListDelimiter(),
//						innerTypeName(typeParam, context)));
//			}
//		}
//		sb.append(namingStrategy.getCloseGeneric());
//		return sb.toString();
//	}
//
//	private String innerTypeName(ResolvedType type, ModelContext context) {
//		if (type.getTypeParameters().size() > 0 && type.getErasedType().getTypeParameters().length > 0) {
//			return genericTypeName(type, context);
//		}
//		return simpleTypeName(type, context);
//	}
//
//	private String simpleTypeName(ResolvedType type, ModelContext context) {
//		Class<?> erasedType = type.getErasedType();
//		if (type instanceof ResolvedPrimitiveType) {
//			return typeNameFor(erasedType);
//		} else if (enumTypeDeterminer.isEnum(erasedType)) {
//			return "string";
//		} else if (type instanceof ResolvedArrayType) {
//			GenericTypeNamingStrategy namingStrategy = context.getGenericNamingStrategy();
//			return String.format("Array%s%s%s", namingStrategy.getOpenGeneric(),
//					simpleTypeName(type.getArrayElementType(), context), namingStrategy.getCloseGeneric());
//		} else if (type instanceof ResolvedObjectType) {
//			String typeName = typeNameFor(erasedType);
//			if (typeName != null) {
//				return typeName;
//			}
//		}
//		return typeName(new ModelNameContext(type.getErasedType(), context.getDocumentationType()));
//	}

//	private String typeName(ModelNameContext context) {
//		TypeNameProviderPlugin selected = typeNameProviders.getPluginOrDefaultFor(context.getDocumentationType(),
//				new DefaultTypeNameProvider());
//		return selected.nameFor(context.getType());
//	}

//}
