

import soot.*;
import soot.jimple.Constant;
import soot.jimple.Jimple;
import soot.jimple.StaticInvokeExpr;
import soot.jimple.Stmt;

import soot.util.Chain;

import java.util.Iterator;
import java.util.List;

//import soot.jimple.spark.pag.PAG;
//import soot.options.SparkOptions;
//import soot.toolkits.graph.DirectedGraph;

/**
 * Created by Sabatu on 3/17/2017.
 */
public class utilities {


    public static Value makeBoxedValue(SootMethod m, Value v, List probe, Type tLocalObj) {
        Body b = m.retrieveActiveBody();
        Local lobj = /*utils.createUniqueLocal(*/getCreateLocal(b, "<loc_object>", tLocalObj);
        Value vfinal = v;
        if (!(v instanceof Constant || v instanceof Local)) {
            Local lValCopy = /*utils.createUniqueLocal(*/getCreateLocal(b, "<loc_box_" + v.getType() + ">", v.getType());
            Stmt sCopyToLocal = Jimple.v().newAssignStmt(lValCopy, v);
            probe.add(sCopyToLocal);
            vfinal = lValCopy;
        }

        if (v.getType() instanceof PrimType) {
            Pair<RefType, SootMethod> refTypeAndCtor = getBoxingTypeAndCtor((PrimType) v.getType());
            Stmt sNewBox = Jimple.v().newAssignStmt(lobj,	Jimple.v().newNewExpr(refTypeAndCtor.first()));
            Stmt sInitBox = Jimple.v().newInvokeStmt(Jimple.v().newSpecialInvokeExpr(lobj,refTypeAndCtor.second().makeRef(), vfinal));
            probe.add(sNewBox);
            probe.add(sInitBox);
        } else {
            assert v.getType() instanceof RefLikeType || (v instanceof StaticInvokeExpr);
            Stmt sCopyRef = Jimple.v().newAssignStmt(lobj, vfinal);
            probe.add(sCopyRef);
        }

        return lobj;
    }
    public static Value makeBoxedValue(SootMethod m, Value v, List probe) {
        return makeBoxedValue(m, v, probe, Scene.v().getSootClass("java.lang.Object").getType());
    }

    public static Local getLocal(Body b, String localName) {
        // look for existing bs local, and return it if found
        Chain locals = b.getLocals();
        for (Iterator itLoc = locals.iterator(); itLoc.hasNext(); ) {
            Local l = (Local)itLoc.next();
            if (l.getName().equals(localName))
                return l;
        }

        return null;
    }
    public static Local getCreateLocal(Body b, String localName, Type t) {
        // try getting it
        Local l = getLocal(b, localName);
        if (l != null) {
            assert l.getType().equals(t); // ensure type is correct
            return l;
        }
        // no luck; create it
        Chain locals = b.getLocals();
        l = Jimple.v().newLocal(localName, t);
        locals.add(l);
        return l;
    }

    public static Pair<RefType,SootMethod> getBoxingTypeAndCtor(PrimType t) {
        if (t instanceof BooleanType) {
            SootClass clsBoxing = Scene.v().getSootClass("java.lang.Boolean");
            return new Pair<RefType,SootMethod>(clsBoxing.getType(), clsBoxing.getMethod("void <init>(boolean)"));
        }
        else if (t instanceof ByteType) {
            SootClass clsBoxing = Scene.v().getSootClass("java.lang.Byte");
            return new Pair<RefType,SootMethod>(clsBoxing.getType(), clsBoxing.getMethod("void <init>(byte)"));
        }
        else if (t instanceof CharType) {
            SootClass clsBoxing = Scene.v().getSootClass("java.lang.Character");
            return new Pair<RefType,SootMethod>(clsBoxing.getType(), clsBoxing.getMethod("void <init>(char)"));
        }
        else if (t instanceof DoubleType) {
            SootClass clsBoxing = Scene.v().getSootClass("java.lang.Double");
            return new Pair<RefType,SootMethod>(clsBoxing.getType(), clsBoxing.getMethod("void <init>(double)"));
        }
        else if (t instanceof FloatType) {
            SootClass clsBoxing = Scene.v().getSootClass("java.lang.Float");
            return new Pair<RefType,SootMethod>(clsBoxing.getType(), clsBoxing.getMethod("void <init>(float)"));
        }
        else if (t instanceof IntType) {
            SootClass clsBoxing = Scene.v().getSootClass("java.lang.Integer");
            return new Pair<RefType,SootMethod>(clsBoxing.getType(), clsBoxing.getMethod("void <init>(int)"));
        }
        else if (t instanceof LongType) {
            SootClass clsBoxing = Scene.v().getSootClass("java.lang.Long");
            return new Pair<RefType,SootMethod>(clsBoxing.getType(), clsBoxing.getMethod("void <init>(long)"));
        }
        else {
            assert t instanceof ShortType;
            SootClass clsBoxing = Scene.v().getSootClass("java.lang.Short");
            return new Pair<RefType,SootMethod>(clsBoxing.getType(), clsBoxing.getMethod("void <init>(short)"));
        }
    }
}